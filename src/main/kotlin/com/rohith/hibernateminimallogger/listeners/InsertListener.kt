package com.rohith.hibernateminimallogger.listeners

import com.rohith.hibernateminimallogger.domain.Scenario
import com.rohith.hibernateminimallogger.domain.TransactionEvent
import com.rohith.hibernateminimallogger.metrics.SampleUpdater
import org.hibernate.event.spi.PostInsertEvent
import org.hibernate.event.spi.PostInsertEventListener
import org.hibernate.persister.entity.EntityPersister

class InsertListener(private val noOfCreationSamplesPerEntity: Int) : PostInsertEventListener {
    override fun requiresPostCommitHanding(persister: EntityPersister?): Boolean = false

    override fun onPostInsert(event: PostInsertEvent?) {
        event?.entity?.javaClass?.name?.let {
            SampleUpdater.insert(
                    it,
                    noOfCreationSamplesPerEntity,
                    Scenario.FINISH,
                    TransactionEvent.INSERT
            )
        }
    }

}
