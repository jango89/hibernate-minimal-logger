package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.metrics.MetricHolder.queryWithExecutionTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

private val LOGGER: Logger = LoggerFactory.getLogger(QueryTimeMetricUpdater::class.java)

object QueryTimeMetricUpdater {

    private val lockForQueryExecutionTime = ReentrantLock()

    fun addToMetric(sql: String, timeInMs: Long) {

        if (lockForQueryExecutionTime.tryLock() || lockForQueryExecutionTime.tryLock(3, TimeUnit.MILLISECONDS)) {
            try {
                lockForQueryExecutionTime.lock()

                val value: Long? = queryWithExecutionTime.merge(sql, timeInMs) { oldValue, newValue ->
                    if (newValue > oldValue) newValue else oldValue
                }
                queryWithExecutionTime[sql] = value
                LOGGER.debug("Sql updated with execution time={}", value)
            } finally {
                lockForQueryExecutionTime.unlock()
            }
        }
    }
}
