package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.domain.StatisticsLoggerType.QUERY_ROWS_COUNT
import com.rohith.hibernateminimallogger.metrics.QueryRowMetricUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(QueryRowStatisticsLogger::class.java)

internal class QueryRowStatisticsLogger {

    fun execute(sql: String, rows: Int, dataStore: DataStore) {

        if (dataStore.isEnabled(QUERY_ROWS_COUNT)) {
            kotlin.runCatching {
                log(dataStore, sql, rows)
                reportMetric(dataStore, rows, sql)
            }.onFailure {
                LOGGER.error("Logging query rows failed {} ", it)
            }
        } else {
            LOGGER.debug("QUERY_ROWS_COUNT logger not enabled")
        }
    }

    private fun log(dataStore: DataStore, sql: String, rows: Int) {
        if (eligibleToLog(dataStore, rows)) {
            LOGGER.info("Hibernate-statistics Rows taken to execute sql={} is rows={}", sql, rows)
        }
    }

    private fun reportMetric(dataStore: DataStore, rows: Int, sql: String) {
        if (eligibleToReport(dataStore, rows)) {
            QueryRowMetricUpdater.addToMetric(sql, rows)
        }
    }

    private fun eligibleToReport(dataStore: DataStore, rows: Int): Boolean =
            (dataStore.canReportMetric).also { log(it, "Report flag is set to false") }
                    && (rows > dataStore.minRowsToReportMetric).also { log(it, "Min-rows expectation failed") }


    private fun eligibleToLog(dataStore: DataStore, rows: Int): Boolean =
            (dataStore.eligibleToLog()) && (rows > dataStore.minRowsToReportMetric)

    private fun log(it: Boolean, message: String) {
        if (!it) {
            LOGGER.debug("Hibernate-statistics {}", message)
        }
    }

}
