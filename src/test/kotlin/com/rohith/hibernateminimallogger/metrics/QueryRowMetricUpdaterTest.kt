package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.stream.Collectors
import java.util.stream.IntStream

class QueryRowMetricUpdaterTest {

    @Test
    fun shouldUpdateWithRows() {
        QueryRowMetricUpdater.addToMetric("first_query_row_update", 1000)
        assertEquals(1000, MetricHolder.queryWithRows["first_query_row_update"])
    }

    @Test
    fun shouldUpdateRow_WithHighestTimeTaken_whenMultipleThreadsTryToUpdate() {

        val service: ThreadPoolExecutor = Executors.newFixedThreadPool(50) as ThreadPoolExecutor

        val randomList = IntStream.range(1000, 1050)
                .boxed()
                .collect(Collectors.toList())

        randomList.map {
            service.submit { QueryRowMetricUpdater.addToMetric("second_query_row_update", it) }
        }
        Thread.sleep(2000)

        assertEquals(randomList.maxOrNull(), MetricHolder.queryWithRows["second_query_row_update"])
    }
}
