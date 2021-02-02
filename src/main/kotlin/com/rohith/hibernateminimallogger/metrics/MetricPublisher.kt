package com.rohith.hibernateminimallogger.metrics

import java.util.*

object MetricPublisher {

    fun queryWithMaxExecutionTime(): Map<String, Long> =
            MetricHolder.queryWithExecutionTime.toMap()
                    .apply { MetricHolder.queryWithExecutionTime.clear() }

    fun queryWithMaxRows(): Map<String, Int> =
            MetricHolder.queryWithRows.toMap()
                    .apply { MetricHolder.queryWithRows.clear() }

    fun executionTimeForInserts(minTimeToReportInMs: Long = 250): Map<String, Long> =
            MetricHolder.insertEntityWithThreadAndTimeReference
                    .mapValues { retrieveMaxTimeFromSamples(it.value) ?: 0 }
                    .filter { minTimeToReportInMs < it.value }
                    .apply { MetricHolder.insertEntityWithThreadAndTimeReference.clear() }

    fun executionTimeForUpdates(minTimeToReportInMs: Long = 250): Map<String, Long> =
            MetricHolder.updateEntityWithThreadAndTimeReference
                    .mapValues { retrieveMaxTimeFromSamples(it.value) ?: 0 }
                    .filter { minTimeToReportInMs < it.value }
                    .apply { MetricHolder.updateEntityWithThreadAndTimeReference.clear() }

    fun executionTimeForDeletes(minTimeToReportInMs: Long = 250): Map<String, Long> =
            MetricHolder.deleteEntityWithThreadAndTimeReference
                    .mapValues { retrieveMaxTimeFromSamples(it.value) ?: 0 }
                    .filter { minTimeToReportInMs < it.value }
                    .apply { MetricHolder.deleteEntityWithThreadAndTimeReference.clear() }

    private fun retrieveMaxTimeFromSamples(threadIdWithStats: WeakHashMap<Long, MetricHolder.TimeStats>) =
            threadIdWithStats
                    .filterValues { it.finish > 0 && it.start > 0 }
                    .map { (it.value.finish - it.value.start) }
                    .maxOrNull()
}
