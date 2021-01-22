package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.metrics.MetricHolder.queryWithRows
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

private val LOGGER: Logger = LoggerFactory.getLogger(QueryRowMetricUpdater::class.java)

object QueryRowMetricUpdater {

    private val lockForQueryRows = ReentrantLock()

    fun addToMetric(sql: String, rows: Int) {

        if (lockForQueryRows.tryLock() || lockForQueryRows.tryLock(3, TimeUnit.MILLISECONDS)) {
            try {
                lockForQueryRows.lock()

                val value: Int? = queryWithRows.merge(sql, rows) { oldValue, newValue ->
                    if (newValue > oldValue) newValue else oldValue
                }
                queryWithRows[sql] = value
                LOGGER.debug("Sql updated with rows count={}", value)

            } finally {
                lockForQueryRows.unlock()
            }
        }
    }
}
