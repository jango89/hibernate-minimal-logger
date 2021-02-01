package com.rohith.hibernateminimallogger

import com.rohith.hibernateminimallogger.hibernateenablers.ListenerIntegrator

class TransactionalStatisticsFactory {

    fun execute(props: Map<String, Any>) {
        ListenerIntegrator(props)
    }
}
