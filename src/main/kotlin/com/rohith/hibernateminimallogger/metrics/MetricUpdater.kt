package com.rohith.hibernateminimallogger.metrics

import com.rohith.hibernateminimallogger.metrics.MetricHolder.addToMetric


internal object MetricUpdater {

    fun updateMaxQueryExecutionTime(sql: String, timeInMs: Long) {
        addToMetric(sql, timeInMs)
    }
}
