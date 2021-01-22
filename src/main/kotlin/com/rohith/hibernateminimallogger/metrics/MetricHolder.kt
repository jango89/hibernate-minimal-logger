package com.rohith.hibernateminimallogger.metrics

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

private val LOGGER: Logger = LoggerFactory.getLogger(MetricHolder::class.java)

internal object MetricHolder {

    private val queryWithExecutionTime = WeakHashMap<String, Long>()
    private val lock = ReentrantLock()

    fun addToMetric(sql: String, timeInMs: Long) {

        if (lock.tryLock() || lock.tryLock(3, TimeUnit.MILLISECONDS)) {
            try {
                lock.lock()

                val value: Long? = queryWithExecutionTime.merge(sql, timeInMs) { oldValue, newValue ->
                    if (newValue > oldValue) newValue else oldValue
                }
                queryWithExecutionTime[sql] = value
                LOGGER.debug("Sql updated with execution value={}", value)
            } finally {
                lock.unlock()
            }
        }
    }

    fun fetchQueryWithExecutionTime() = queryWithExecutionTime
}
