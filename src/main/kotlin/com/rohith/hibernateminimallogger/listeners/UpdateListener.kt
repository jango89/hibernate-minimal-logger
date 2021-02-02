package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PostUpdateEvent
import org.hibernate.event.spi.PostUpdateEventListener
import org.hibernate.persister.entity.EntityPersister

class UpdateListener(private val noOfUpdationSamplesPerEntity: Int) : PostUpdateEventListener {

    override fun requiresPostCommitHanding(persister: EntityPersister?): Boolean = false

    override fun onPostUpdate(event: PostUpdateEvent?) {
        event?.session?.getEntityName(event?.entity)?.let {
            SampleUpdater.update(
                    it,
                    noOfUpdationSamplesPerEntity,
                    Scenario.FINISH,
                    TransactionEvent.UPDATE
            )
        }
    }
}
