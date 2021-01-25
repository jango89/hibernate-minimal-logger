package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test

class MetricPublisherTest {

    @Test
    fun shouldPublishMetric_whenQueryWithExecutionTimeIsPresent() {
        MetricHolder.queryWithExecutionTime["first"] = 10
        assertEquals(10L, MetricPublisher.queryWithMaxExecutionTime()["first"])
    }

    @Test
    fun shouldPublishMetric_whenQueryWithRowsIsPresent() {
        MetricHolder.queryWithRows["second"] = 10
        assertEquals(10, MetricPublisher.queryWithMaxRows()["second"])
    }
}
