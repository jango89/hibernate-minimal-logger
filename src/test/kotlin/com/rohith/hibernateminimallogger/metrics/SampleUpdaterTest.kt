package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import org.junit.Assert.assertTrue
import org.junit.Test

class SampleUpdaterTest {

    @Test
    fun shouldUpdateMetric_whenUpdateEntryIsProcessed() {
        SampleUpdater.update("entity", 10, Scenario.START, TransactionEvent.UPDATE);
        Thread.sleep(10)
        SampleUpdater.update("entity", 10, Scenario.FINISH, TransactionEvent.UPDATE);
        Thread.sleep(10)
        SampleUpdater.update("entity1", 10, Scenario.START, TransactionEvent.UPDATE);
        Thread.sleep(10)
        SampleUpdater.update("entity1", 10, Scenario.FINISH, TransactionEvent.UPDATE);
        Thread.sleep(10)

        val executionTimeForUpdates = MetricPublisher.executionTimeForUpdates(0)
        assertTrue(executionTimeForUpdates.size == 2)
        assertTrue(executionTimeForUpdates["entity"]!! > 0)
        assertTrue(executionTimeForUpdates["entity1"]!! > 0)
    }

    @Test
    fun shouldUpdateMetric_whenInsertEntryIsProcessed() {
        SampleUpdater.insert("entity", 10, Scenario.START, TransactionEvent.INSERT);
        Thread.sleep(10)
        SampleUpdater.insert("entity", 10, Scenario.FINISH, TransactionEvent.INSERT);
        Thread.sleep(10)
        SampleUpdater.insert("entity1", 10, Scenario.START, TransactionEvent.INSERT);
        Thread.sleep(10)
        SampleUpdater.insert("entity1", 10, Scenario.FINISH, TransactionEvent.INSERT);
        Thread.sleep(10)

        val executionTimeForInserts = MetricPublisher.executionTimeForInserts(0)
        assertTrue(executionTimeForInserts.size == 2)
        assertTrue(executionTimeForInserts["entity"]!! > 0)
        assertTrue(executionTimeForInserts["entity1"]!! > 0)
    }

    @Test
    fun shouldUpdateMetric_whenDeleteEntryIsProcessed() {
        SampleUpdater.delete("entity", 10, Scenario.START, TransactionEvent.DELETE);
        Thread.sleep(10)
        SampleUpdater.delete("entity", 10, Scenario.FINISH, TransactionEvent.DELETE);
        Thread.sleep(10)
        SampleUpdater.delete("entity1", 10, Scenario.START, TransactionEvent.DELETE);
        Thread.sleep(10)
        SampleUpdater.delete("entity1", 10, Scenario.FINISH, TransactionEvent.DELETE);
        Thread.sleep(10)

        val executionTimeForDeletes = MetricPublisher.executionTimeForDeletes(0)
        assertTrue(executionTimeForDeletes.size == 2)
        assertTrue(executionTimeForDeletes["entity"]!! > 0)
        assertTrue(executionTimeForDeletes["entity1"]!! > 0)
    }
}
