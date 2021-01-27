package com.rohith.hibernateminimallogger.metrics

object MetricPublisher {

    fun queryWithMaxExecutionTime(): Map<String, Long> =
            MetricHolder.queryWithExecutionTime.toMap()
                    .apply { MetricHolder.queryWithExecutionTime.clear() }

    fun queryWithMaxRows(): Map<String, Int> =
            MetricHolder.queryWithRows.toMap()
                    .apply { MetricHolder.queryWithRows.clear() }
}
