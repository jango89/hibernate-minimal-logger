package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PreDeleteEvent
import org.hibernate.event.spi.PreDeleteEventListener

internal class PreDeleteListener(private val noOfDeletionSamplesPerEntity: Int) : PreDeleteEventListener {

    override fun onPreDelete(event: PreDeleteEvent?): Boolean {
        event?.session?.getEntityName(event?.entity)?.let {
            SampleUpdater.delete(
                    it,
                    noOfDeletionSamplesPerEntity,
                    Scenario.START,
                    TransactionEvent.DELETE
            )
        }
        return true
    }

}
