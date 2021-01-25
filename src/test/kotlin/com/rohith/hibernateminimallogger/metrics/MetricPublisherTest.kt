package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test

class MetricPublisherTest {

    @Test
    fun shouldPublishMetric_whenQueryWithExecutionTimeIsPresent() {
        MetricHolder.queryWithExecutionTime["first_publish"] = 10
        assertEquals(10L, MetricPublisher.queryWithMaxExecutionTime()["first_publish"])
    }

    @Test
    fun shouldPublishMetric_whenQueryWithRowsIsPresent() {
        MetricHolder.queryWithRows["second_publish"] = 10
        assertEquals(10, MetricPublisher.queryWithMaxRows()["second_publish"])
    }
}
