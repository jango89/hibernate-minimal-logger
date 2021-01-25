package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.random.Random


class QueryTimeMetricUpdaterTest {

    @Test
    fun shouldUpdateWithTimeTaken() {
        QueryTimeMetricUpdater.addToMetric("first_query_time_update", 1000)
        assertEquals(1000L, MetricHolder.queryWithExecutionTime["first_query_time_update"])
    }

    @Test
    fun shouldUpdate_WithHighestTimeTaken_whenMultipleThreadsTryToUpdate() {

        val service: ThreadPoolExecutor = Executors.newFixedThreadPool(10) as ThreadPoolExecutor

        val randomList = arrayListOf<Long>()

        for (i in 0 until 10) {
            service.submit {
                val longVal = Random.nextLong(1000, 1010)
                randomList.add(longVal)
                QueryTimeMetricUpdater.addToMetric("second_query_row_update", longVal)
            }
        }
        Thread.sleep(1000)

        assertEquals(randomList.maxOrNull(), MetricHolder.queryWithExecutionTime["second_query_row_update"])
    }

}
