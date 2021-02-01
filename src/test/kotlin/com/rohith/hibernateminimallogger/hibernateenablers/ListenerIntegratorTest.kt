package com.rohith.hibernateminimallogger.hibernateenablers

import org.hibernate.event.service.spi.DuplicationStrategy
import org.hibernate.event.service.spi.EventListenerGroup
import org.hibernate.event.service.spi.EventListenerRegistry
import org.hibernate.event.spi.EventType
import org.hibernate.service.Service
import org.hibernate.service.ServiceRegistry
import org.hibernate.service.spi.ServiceBinding
import org.hibernate.service.spi.ServiceRegistryImplementor
import org.hibernate.service.spi.SessionFactoryServiceRegistry
import org.junit.Assert
import org.junit.Test

class ListenerIntegratorTest {

    @Test
    fun shouldAddIntegrators_whenInsertPropertyIsPresent() {

        val list = mutableListOf<Any>()
        val integrator = ListenerIntegrator(mapOf("min.hibernate.stats.logging.updates.enabled" to "true"))
                .integrators.first()
        integrator.integrate(null, null, Factory(EventListenerRegistrySpy(list)))
        Assert.assertEquals(2, list.size)
    }

    private class Factory(val eventListener: EventListenerRegistrySpy) : SessionFactoryServiceRegistry {


        override fun getParentServiceRegistry(): ServiceRegistry {
            TODO("Not yet implemented")
        }

        override fun <R : Service?> getService(serviceRole: Class<R>?): R {
            return eventListener as R
        }

        override fun <R : Service?> locateServiceBinding(serviceRole: Class<R>?): ServiceBinding<R> {
            TODO("Not yet implemented")
        }

        override fun destroy() {
            TODO("Not yet implemented")
        }

        override fun registerChild(child: ServiceRegistryImplementor?) {
            TODO("Not yet implemented")
        }

        override fun deRegisterChild(child: ServiceRegistryImplementor?) {
            TODO("Not yet implemented")
        }

    }

    private class EventListenerRegistrySpy(var eventListeners: MutableList<Any>) : EventListenerRegistry {


        override fun <T : Any?> getEventListenerGroup(eventType: EventType<T>?): EventListenerGroup<T> {
            TODO("Not yet implemented")
        }

        override fun addDuplicationStrategy(strategy: DuplicationStrategy?) {
            TODO("Not yet implemented")
        }

        override fun <T : Any?> setListeners(type: EventType<T>?, vararg listeners: Class<out T>?) {
            TODO("Not yet implemented")
        }

        override fun <T : Any?> setListeners(type: EventType<T>?, vararg listeners: T) {
            TODO("Not yet implemented")
        }

        override fun <T : Any?> appendListeners(type: EventType<T>?, vararg listeners: Class<out T>?) {
            TODO("Not yet implemented")
        }

        override fun <T : Any?> appendListeners(type: EventType<T>?, vararg listeners: T) {
            eventListeners.add(listeners[0]!!)
        }

        override fun <T : Any?> prependListeners(type: EventType<T>?, vararg listeners: Class<out T>?) {
        }

        override fun <T : Any?> prependListeners(type: EventType<T>?, vararg listeners: T) {
            TODO("Not yet implemented")
        }

    }
}
