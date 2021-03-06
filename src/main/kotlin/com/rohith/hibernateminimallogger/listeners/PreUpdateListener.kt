package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PreUpdateEvent
import org.hibernate.event.spi.PreUpdateEventListener

class PreUpdateListener(private val noOfUpdationSamplesPerEntity: Int) : PreUpdateEventListener {

    override fun onPreUpdate(event: PreUpdateEvent?): Boolean {
        event?.entity?.javaClass?.name?.let {
            SampleUpdater.update(
                    it,
                    noOfUpdationSamplesPerEntity,
                    Scenario.START,
                    TransactionEvent.UPDATE
            )
        }

        return false
    }

}
