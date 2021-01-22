package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.metrics.MetricUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val LOGGER: Logger = LoggerFactory.getLogger(QueryTimeStatisticsLogger::class.java)

internal class QueryTimeStatisticsLogger {

    fun execute(sql: String, rows: Int, timeInMs: Long, dataStore: DataStore) {
        try {
            log(dataStore, sql, timeInMs, rows)
            reportMetric(dataStore, timeInMs, sql)
        } catch (ex: Exception) {
            LOGGER.warn("Metric cannot be reported", ex)
        }
    }

    private fun log(dataStore: DataStore, sql: String, timeInMs: Long, rows: Int) {
        if (dataStore.eligibleToLog()) {
            LOGGER.info("Hibernate-statistics Time taken to execute sql={} is time-ms={} for rows={}", sql, timeInMs, rows)
        } else {
            LOGGER.debug("Hibernate-statistics Logging setting disabled ")
        }
    }

    private fun reportMetric(dataStore: DataStore, timeInMs: Long, sql: String) {
        if (dataStore.eligibleToReport(timeInMs)) {
            MetricUpdater.updateMaxQueryExecutionTime(sql, timeInMs)
        } else {
            LOGGER.debug("Hibernate-statistics reporting metric setting disabled ")
        }
    }
}
