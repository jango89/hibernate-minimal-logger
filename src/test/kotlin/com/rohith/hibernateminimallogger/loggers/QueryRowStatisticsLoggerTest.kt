package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.metrics.MetricPublisher
import org.junit.Assert.assertEquals
import org.junit.Test

class QueryRowStatisticsLoggerTest {

    @Test
    fun shouldLogMetric_whenLoggingIsEnabled() {
        QueryRowStatisticsLogger().execute("first_log_metric", 100, DataStore(canReportMetric = true))
        assertEquals(100, MetricPublisher.queryWithMaxRows()["first_log_metric"])
    }

    @Test
    fun shouldLogMetric_whenConfiguredRowsMinCountIsLessThanFound() {
        QueryRowStatisticsLogger().execute("second_log_metric", 100, DataStore(canReportMetric = true, minRowsToReportMetric = 10))
        assertEquals(100, MetricPublisher.queryWithMaxRows()["second_log_metric"])
    }

    @Test
    fun shouldNotLogMetric_whenConfiguredRowsMinCountIsGreaterThanFound() {
        QueryRowStatisticsLogger().execute("third_log_metric", 100, DataStore(canReportMetric = true, minRowsToReportMetric = 200))
        assertEquals(null, MetricPublisher.queryWithMaxRows()["third_log_metric"])
    }

    @Test
    fun shouldNotLogMetric_whenReportMetricFlagIsFalse() {
        QueryRowStatisticsLogger().execute("fourth_log_metric", 1000, DataStore(canReportMetric = false, minRowsToReportMetric = 200))
        assertEquals(null, MetricPublisher.queryWithMaxRows()["fourth_log_metric"])
    }
}
