package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.metrics.QueryTimeMetricUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(QueryTimeStatisticsLogger::class.java)

internal class QueryTimeStatisticsLogger {

    fun execute(sql: String, timeInMs: Long, dataStore: DataStore) {
        try {
            log(dataStore, sql, timeInMs)
            reportMetric(dataStore, timeInMs, sql)
        } catch (ex: Exception) {
            LOGGER.warn("Metric cannot be reported", ex)
        }
    }

    private fun log(dataStore: DataStore, sql: String, timeInMs: Long) {
        if (dataStore.eligibleToLog()) {
            LOGGER.info("Hibernate-statistics Time taken to execute sql={} is time-ms={}", sql, timeInMs)
        } else {
            LOGGER.debug("Hibernate-statistics Logging setting disabled ")
        }
    }

    private fun reportMetric(dataStore: DataStore, timeInMs: Long, sql: String) {
        if (eligibleToReport(dataStore, timeInMs)) {
            QueryTimeMetricUpdater.addToMetric(sql, timeInMs)
        } else {
            LOGGER.debug("Hibernate-statistics reporting metric setting disabled ")
        }
    }

    private fun eligibleToReport(dataStore: DataStore, timeInMs: Long) = dataStore.canReportMetric && timeInMs >= dataStore.minTimeInMilliSecondToReportMetric
}
