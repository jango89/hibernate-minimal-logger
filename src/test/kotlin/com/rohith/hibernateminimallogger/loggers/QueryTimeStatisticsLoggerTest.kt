package com.rohith.hibernateminimallogger.loggers

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.metrics.MetricPublisher
import org.junit.Assert
import org.junit.Test

class QueryTimeStatisticsLoggerTest {

    @Test
    fun shouldLogMetric_whenLoggingIsEnabled() {
        QueryTimeStatisticsLogger().execute("first_row_metric", 100, DataStore(canReportMetric = true, minTimeInMilliSecondToReportMetric = 10))
        Assert.assertEquals(100L, MetricPublisher.queryWithMaxExecutionTime()["first_row_metric"])
    }

    @Test
    fun shouldLogMetric_whenConfiguredTimeIsLessThanFound() {
        QueryTimeStatisticsLogger().execute("second_row_metric", 100, DataStore(canReportMetric = true, minTimeInMilliSecondToReportMetric = 10))
        Assert.assertEquals(100L, MetricPublisher.queryWithMaxExecutionTime()["second_row_metric"])
    }

    @Test
    fun shouldNotLogMetric_whenConfiguredRTimeIsGreaterThanFound() {
        QueryTimeStatisticsLogger().execute("third_row_metric", 100, DataStore(canReportMetric = true, minTimeInMilliSecondToReportMetric = 200))
        Assert.assertEquals(null, MetricPublisher.queryWithMaxExecutionTime()["third_row_metric"])
    }

    @Test
    fun shouldNotLogMetric_whenReportMetricFlagIsFalse() {
        QueryTimeStatisticsLogger().execute("fourth_row_metric", 1000, DataStore(canReportMetric = false, minTimeInMilliSecondToReportMetric = 200))
        Assert.assertEquals(null, MetricPublisher.queryWithMaxExecutionTime()["fourth_row_metric"])
    }
}
