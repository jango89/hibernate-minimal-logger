package com.rohith.hibernateminimallogger

import com.rohith.hibernateminimallogger.domain.DataStore
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.stat.spi.StatisticsFactory
import org.hibernate.stat.spi.StatisticsImplementor

class MinimalStatisticsFactory : StatisticsFactory {

    override fun buildStatistics(sessionFactory: SessionFactoryImplementor): StatisticsImplementor {
        return create(sessionFactory.properties)
    }

    private fun create(props: Map<String, Any>): StatisticsLoggingEnabler = StatisticsLoggingEnabler(DataStore().reInitialize(props))
}
