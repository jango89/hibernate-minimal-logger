package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PreInsertEvent
import org.hibernate.event.spi.PreInsertEventListener

class PreInsertListener(private val noOfCreationSamplesPerEntity: Int) : PreInsertEventListener {

    override fun onPreInsert(event: PreInsertEvent?): Boolean {
        event?.session?.getEntityName(event?.entity)?.let {
            SampleUpdater.insert(
                    it,
                    noOfCreationSamplesPerEntity,
                    Scenario.START,
                    TransactionEvent.INSERT
            )
        }
        return true
    }
}
