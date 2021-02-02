package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PostDeleteEvent
import org.hibernate.event.spi.PostDeleteEventListener
import org.hibernate.persister.entity.EntityPersister

internal class DeleteListener(private val noOfDeletionSamplesPerEntity: Int) : PostDeleteEventListener {

    override fun requiresPostCommitHanding(persister: EntityPersister?): Boolean = false

    override fun onPostDelete(event: PostDeleteEvent?) {
        event?.entity?.javaClass?.name?.let {
            SampleUpdater.delete(
                    it,
                    noOfDeletionSamplesPerEntity,
                    Scenario.FINISH,
                    TransactionEvent.DELETE
            )
        }
    }

}
