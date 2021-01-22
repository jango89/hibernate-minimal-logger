package com.rohith.hibernateminimallogger.metrics

object MetricPublisher {

    fun queryWithMaxExecutionTime(): Map<String, Long> =
            MetricHolder.fetchQueryWithExecutionTime().toMap()
}
