package com.rohith.hibernateminimallogger

import com.rohith.hibernateminimallogger.domain.TransactionalLoggingDataStore
import com.rohith.hibernateminimallogger.listeners.*
import org.hibernate.boot.Metadata
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.event.service.spi.EventListenerRegistry
import org.hibernate.event.spi.EventType
import org.hibernate.integrator.spi.Integrator
import org.hibernate.jpa.boot.spi.IntegratorProvider
import org.hibernate.service.spi.SessionFactoryServiceRegistry

class ListenerIntegrator(private val props: Map<String, Any>) : IntegratorProvider {

    override fun getIntegrators(): MutableList<Integrator> {
        return mutableListOf(TransactionalIntegrator(props))
    }

    private class TransactionalIntegrator(private val props: Map<String, Any>) : Integrator {

        private val dataStore: TransactionalLoggingDataStore = TransactionalLoggingDataStore().reInitialize(props)

        override fun integrate(metadata: Metadata?, sessionFactory: SessionFactoryImplementor?, serviceRegistry: SessionFactoryServiceRegistry?) {

            with(serviceRegistry!!.getService(EventListenerRegistry::class.java)) {
                enableDeleteListeners(this)
                enableUpdateListeners(this)
                enableInsertListeners(this)
            }
        }

        private fun enableInsertListeners(eventListenerRegistry: EventListenerRegistry) {
            if (dataStore.canCreateUpdateListeners) {
                eventListenerRegistry.prependListeners(EventType.PRE_UPDATE,
                        PreUpdateListener(dataStore.noOfUpdationSamplesPerEntity))
                eventListenerRegistry.prependListeners(EventType.POST_UPDATE,
                        UpdateListener(dataStore.noOfUpdationSamplesPerEntity))
            }
        }

        private fun enableUpdateListeners(eventListenerRegistry: EventListenerRegistry) {
            if (dataStore.canCreateInsertListeners) {
                eventListenerRegistry.prependListeners(EventType.PRE_INSERT,
                        PreInsertListener(dataStore.noOfCreationSamplesPerEntity))
                eventListenerRegistry.prependListeners(EventType.POST_INSERT,
                        InsertListener(dataStore.noOfCreationSamplesPerEntity))
            }
        }

        private fun enableDeleteListeners(eventListenerRegistry: EventListenerRegistry) {
            if (dataStore.canCreateDeleteListeners) {
                eventListenerRegistry.prependListeners(EventType.PRE_DELETE,
                        PreDeleteListener(dataStore.noOfDeletionSamplesPerEntity))
                eventListenerRegistry.prependListeners(EventType.POST_DELETE,
                        DeleteListener(dataStore.noOfDeletionSamplesPerEntity))
            }
        }

        override fun disintegrate(sessionFactory: SessionFactoryImplementor?, serviceRegistry: SessionFactoryServiceRegistry?) {
        }
    }

}
