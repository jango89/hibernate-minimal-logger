package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.metrics.MetricHolder.queryWithRows
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.locks.ReentrantReadWriteLock

private val LOGGER: Logger = LoggerFactory.getLogger(QueryRowMetricUpdater::class.java)

object QueryRowMetricUpdater {

    private val lockForQueryRows = ReentrantReadWriteLock()

    fun addToMetric(sql: String, rows: Int) {

        val writeLock = lockForQueryRows.writeLock()
        try {
            if (writeLock.tryLock(10, MILLISECONDS)) {
                val rowsToBeUpdated: Int? = queryWithRows.merge(sql, rows) { oldValue, newValue ->
                    if (newValue > oldValue) newValue else oldValue
                }
                queryWithRows[sql] = rowsToBeUpdated

                LOGGER.debug("Sql updated with rows count={}, against rows inserted={}", rowsToBeUpdated, rows)
            }

        } finally {
            writeLock.unlock()
        }
    }
}
