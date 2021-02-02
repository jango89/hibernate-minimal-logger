package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import org.junit.jupiter.api.Test

class SampleUpdaterWithValueTest {

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
    }
}
