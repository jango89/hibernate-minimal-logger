package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.domain.StatisticsLoggerType.QUERY_EXECUTION_TIME
import com.rohith.hibernateminimallogger.metrics.QueryTimeMetricUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(QueryTimeStatisticsLogger::class.java)

internal class QueryTimeStatisticsLogger {

    fun execute(sql: String, timeInMs: Long, dataStore: DataStore) {

        if (dataStore.isEnabled(QUERY_EXECUTION_TIME)) {
            kotlin.runCatching {
                log(dataStore, sql, timeInMs)
                reportMetric(dataStore, timeInMs, sql)
            }.onFailure {
                LOGGER.error("Logging query time failed {} ", it)
            }
        } else {
            LOGGER.debug("QUERY_EXECUTION_TIME logger not enabled")
        }
    }

    private fun log(dataStore: DataStore, sql: String, timeInMs: Long) {
        if (eligibleToLog(dataStore, timeInMs)) {
            LOGGER.info("Hibernate-statistics Time taken to execute sql={} is time-ms={}", sql, timeInMs)
        }
    }

    private fun reportMetric(dataStore: DataStore, timeInMs: Long, sql: String) {
        if (eligibleToReport(dataStore, timeInMs)) {
            QueryTimeMetricUpdater.addToMetric(sql, timeInMs)
        } else {
            LOGGER.debug("Hibernate-statistics reporting metric setting disabled ")
        }
    }

    private fun eligibleToReport(dataStore: DataStore, timeInMs: Long) =
            dataStore.canReportMetric.also { log(it, "Report flag is set to false") }
                    && (timeInMs >= dataStore.minTimeInMilliSecondToReportMetric).also { log(it, "Min-time expectation failed") }

    private fun eligibleToLog(dataStore: DataStore, timeInMs: Long) =
            dataStore.eligibleToLog() && (timeInMs >= dataStore.minTimeInMilliSecondToReportMetric)

    private fun log(it: Boolean, message: String) {
        if (!it) {
            LOGGER.debug("Hibernate-statistics {}", message)
        }
    }
}
