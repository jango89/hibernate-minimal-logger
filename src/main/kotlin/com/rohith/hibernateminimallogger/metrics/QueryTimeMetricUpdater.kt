package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.metrics.MetricHolder.queryWithExecutionTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.locks.ReentrantReadWriteLock

private val LOGGER: Logger = LoggerFactory.getLogger(QueryTimeMetricUpdater::class.java)

object QueryTimeMetricUpdater {

    private val lockForQueryExecutionTime = ReentrantReadWriteLock()

    fun addToMetric(sql: String, timeInMs: Long) {

        val writeLock = lockForQueryExecutionTime.writeLock()
        try {
            writeLock.lock()

            val updatedTimeInMs: Long? = queryWithExecutionTime.merge(sql, timeInMs) { oldValue, newValue ->
                if (newValue > oldValue) newValue else oldValue
            }
            queryWithExecutionTime[sql] = updatedTimeInMs

            LOGGER.debug("Sql updated with execution time={}, against timeMs inserted={}", updatedTimeInMs, timeInMs)

        } finally {
            writeLock.unlock()
        }
    }
}
