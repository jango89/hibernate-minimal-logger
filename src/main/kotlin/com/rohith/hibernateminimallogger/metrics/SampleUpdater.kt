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
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.locks.ReentrantReadWriteLock

private val LOGGER: Logger = LoggerFactory.getLogger(SampleUpdater::class.java)
private const val LOCK_IN_MS: Long = 2

internal object SampleUpdater {

    private val updateLock = ReentrantReadWriteLock()
    private val insertLock = ReentrantReadWriteLock()
    private val deleteLock = ReentrantReadWriteLock()

    fun upsert(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            updateMetric(updateLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    fun delete(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            updateMetric(deleteLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    fun insert(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) =
            updateMetric(insertLock, entityName, maxNoOfSamplers, scenario, transactionEvent)

    private fun updateMetric(lock: ReentrantReadWriteLock, entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) {
        val writeLock = lock.writeLock()
        try {
            if (writeLock.tryLock(LOCK_IN_MS, MILLISECONDS)) {
                addToMetric(entityName, maxNoOfSamplers, scenario, transactionEvent)
            }
        } finally {
            writeLock.unlock()
        }
    }

    private fun addToMetric(entityName: String, maxNoOfSamplers: Int, scenario: Scenario, transactionEvent: TransactionEvent) {

        val samples = samples(transactionEvent, entityName)
        if (canAdd(samples, maxNoOfSamplers)) {
            val timeStats = samples.getOrPut(currentThread().id, { WeakReference(MetricHolder.TimeStats()) })
            updateTime(scenario, timeStats)
            LOGGER.debug("Insert sample added for entity={}", entityName)
        }
    }

    private fun samples(transactionEvent: TransactionEvent, entityName: String): WeakHashMap<Long, WeakReference<MetricHolder.TimeStats>> =
            when (transactionEvent) {
                TransactionEvent.INSERT -> insertEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
                TransactionEvent.UPDATE -> updateEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
                TransactionEvent.DELETE -> deleteEntityWithThreadAndTimeReference.getOrPut(entityName, { WeakHashMap() })
            }

    private fun updateTime(scenario: Scenario, timeStats: WeakReference<MetricHolder.TimeStats>) =
            when (scenario) {
                START -> timeStats.get()!!.start = currentTimeMillis()
                FINISH -> timeStats.get()!!.finish = currentTimeMillis()
            }

    private fun canAdd(samples: WeakHashMap<Long, WeakReference<MetricHolder.TimeStats>>, maxNoOfSamplers: Int) =
            samples.size < maxNoOfSamplers
}

