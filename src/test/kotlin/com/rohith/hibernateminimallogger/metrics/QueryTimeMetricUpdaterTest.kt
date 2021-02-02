package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.stream.Collectors
import java.util.stream.LongStream


class QueryTimeMetricUpdaterTest {

    @Test
    fun shouldUpdateWithTimeTaken() {
        QueryTimeMetricUpdater.addToMetric("first_query_time_update", 1000)
        assertEquals(1000L, MetricHolder.queryWithExecutionTime["first_query_time_update"])
    }

    @Test
    fun shouldUpdate_WithHighestTimeTaken_whenMultipleThreadsTryToUpdate() {

        val service: ThreadPoolExecutor = Executors.newFixedThreadPool(50) as ThreadPoolExecutor

        val randomList = LongStream.range(1000, 1050)
                .boxed()
                .collect(Collectors.toList())

        randomList.map {
            service.submit { QueryTimeMetricUpdater.addToMetric("second_query_row_update", it) }
        }
        Thread.sleep(2000)

        assertEquals(randomList.maxOrNull(), MetricHolder.queryWithExecutionTime["second_query_row_update"])
    }

}
