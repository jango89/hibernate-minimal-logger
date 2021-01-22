package com.rohith.hibernateminimallogger.metrics

object MetricPublisher {

    fun queryWithMaxExecutionTime(): Map<String, Long> =
            MetricHolder.queryWithExecutionTime.toMap()

    fun queryWithMaxRows(): Map<String, Int> =
            MetricHolder.queryWithRows.toMap()
}
