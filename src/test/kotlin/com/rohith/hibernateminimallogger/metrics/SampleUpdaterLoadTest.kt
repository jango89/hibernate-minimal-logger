package com.rohith.hibernateminimallogger.metrics

import org.jsmart.zerocode.core.domain.LoadWith
import org.jsmart.zerocode.core.domain.TestMapping
import org.jsmart.zerocode.core.domain.TestMappings
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@LoadWith("load_generation.properties")
@ExtendWith(ParallelLoadExtension::class)
class SampleUpdaterLoadTest {

    @Test
    @DisplayName("Testing parallel Sample Updater")
    @TestMappings(TestMapping(testClass = SampleUpdaterWithValueTest::class, testMethod = "shouldUpdateMetric_whenUpdateEntryIsProcessed"),
            TestMapping(testClass = SampleUpdaterWithValueTest::class, testMethod = "shouldUpdateMetric_whenInsertEntryIsProcessed"),
            TestMapping(testClass = SampleUpdaterWithValueTest::class, testMethod = "shouldUpdateMetric_whenDeleteEntryIsProcessed"))
    fun shouldLoadTest_thenTestAllPossiblitiesOfDataStored() {
        MetricHolder.insertEntityWithThreadAndTimeReference.forEach { entry ->
            assertEquals(10, entry.value.size)
        }

        MetricHolder.updateEntityWithThreadAndTimeReference.forEach { entry ->
            assertEquals(10, entry.value.size)
        }

        MetricHolder.deleteEntityWithThreadAndTimeReference.forEach { entry ->
            assertEquals(10, entry.value.size)
        }

        assertEquals(2, MetricPublisher.executionTimeForUpdates(0).size)
        assertEquals(2, MetricPublisher.executionTimeForInserts(0).size)
        assertEquals(2, MetricPublisher.executionTimeForDeletes(0).size)
    }
}
