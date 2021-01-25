package com.rohith.hibernateminimallogger.metrics

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.random.Random

class QueryRowMetricUpdaterTest {
    @Test
    fun shouldUpdateWithRows() {
        QueryRowMetricUpdater.addToMetric("first", 1000)
        assertEquals(1000, MetricHolder.queryWithRows["first"])
    }

    @Test
    fun shouldUpdateRow_WithHighestTimeTaken_whenMultipleThreadsTryToUpdate() {

        val service: ThreadPoolExecutor = Executors.newFixedThreadPool(10) as ThreadPoolExecutor

        val randomList = arrayListOf<Int>()

        for (i in 0 until 10) {
            service.submit {
                val intVal = Random.nextInt(1000, 1010)
                randomList.add(intVal)
                QueryRowMetricUpdater.addToMetric("second", intVal)
            }
        }
        Thread.sleep(1000)

        assertEquals(randomList.maxOrNull(), MetricHolder.queryWithRows["second"])
    }
}
