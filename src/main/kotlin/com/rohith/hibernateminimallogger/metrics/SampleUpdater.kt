package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.Scenario.FINISH
import com.rohith.hibernateminimallogger.domain.Scenario.START
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.MetricHolder.deleteEntityWithThreadAndTimeReference
import com.rohith.hibernateminimallogger.metrics.MetricHolder.insertEntityWithThreadAndTimeReference
import com.rohith.hibernateminimallogger.metrics.MetricHolder.updateEntityWithThreadAndTimeReference
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock

private val LOGGER: Logger = LoggerFactory.getLogger(SampleUpdater::class.java)
private const val LOCK_IN_MS: Long = 5

internal object SampleUpdater {

    private val updateLock = ReentrantReadWriteLock(true)
    private val insertLock = ReentrantReadWriteLock(true)
    private val deleteLock = ReentrantReadWriteLock(true)

    fun update(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            record(updateLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    fun delete(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            record(deleteLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    fun insert(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            record(insertLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    private fun record(lock: ReentrantReadWriteLock, entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) {
        val writeLock = lock.writeLock()
        try {
            if (writeLock.tryLock(LOCK_IN_MS, TimeUnit.MILLISECONDS)) {
                addToMetric(entityName, maxNoOfSamplers, scenario, transactionEvent)
            }
        } finally {
            writeLock.unlock()
        }
    }

    private fun addToMetric(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) {

        val samples = samples(transactionEvent, entityName)
        if (canAdd(samples, maxNoOfSamplers)) {
            val timeStats = samples.getOrPut(currentThread().id, { MetricHolder.TimeStats() })
            updateTime(scenario, timeStats)
            LOGGER.debug("Transaction={} with scenario={} added for entity={}", transactionEvent, scenario, entityName)
        }
    }

    private fun samples(transactionEvent: TransactionEvent, entityName: String): WeakHashMap<Long, MetricHolder.TimeStats> =
            when (transactionEvent) {
                TransactionEvent.INSERT -> insertEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
                TransactionEvent.UPDATE -> updateEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
                TransactionEvent.DELETE -> deleteEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
            }

    private fun updateTime(scenario: Scenario, timeStats: MetricHolder.TimeStats) =
            when (scenario) {
                START -> timeStats?.start = currentTimeMillis()
                FINISH -> timeStats?.finish = currentTimeMillis()
            }

    private fun canAdd(samples: WeakHashMap<Long, MetricHolder.TimeStats>, maxNoOfSamplers: Int) =
            samples.size < maxNoOfSamplers
}

