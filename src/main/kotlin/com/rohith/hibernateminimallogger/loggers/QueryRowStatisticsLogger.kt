package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.metrics.QueryRowMetricUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(QueryRowStatisticsLogger::class.java)

internal class QueryRowStatisticsLogger {

    fun execute(sql: String, rows: Int, dataStore: DataStore) {
        try {
            log(dataStore, sql, rows)
            reportMetric(dataStore, rows, sql)
        } catch (ex: Exception) {
            LOGGER.warn("Metric cannot be reported", ex)
        }
    }

    private fun log(dataStore: DataStore, sql: String, rows: Int) {
        if (dataStore.eligibleToLog()) {
            LOGGER.info("Hibernate-statistics Rows taken to execute sql={} is rows={}", sql, rows)
        } else {
            LOGGER.debug("Hibernate-statistics Logging setting disabled ")
        }
    }

    private fun reportMetric(dataStore: DataStore, rows: Int, sql: String) {
        if (eligibleToReport(dataStore, rows)) {
            QueryRowMetricUpdater.addToMetric(sql, rows)
        } else {
            LOGGER.debug("Hibernate-statistics reporting metric setting disabled ")
        }
    }

    private fun eligibleToReport(dataStore: DataStore, rows: Int) = dataStore.canReportMetric && rows > dataStore.minRowsToReportMetric
}
