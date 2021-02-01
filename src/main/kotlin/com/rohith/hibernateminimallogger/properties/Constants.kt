package com.rohith.hibernateminimallogger.properties

object Constants {

    const val LOGGING_ENABLED = "min.hibernate.stats.logging.enabled"
    const val METRICS_REPORT_ENABLED = "min.hibernate.stats.metrics.publish"
    const val LOGGERS = "min.hibernate.stats.loggers"
    const val QUERY_MIN_EXECUTION_TIME_IN_MS = "min.hibernate.stats.query.logging.min.executionTime.millis"
    const val QUERY_MIN_ROWS = "min.hibernate.stats.query.logging.min.rows"
    const val UPDATE_LISTENERS_ENABLED = "min.hibernate.stats.logging.updates.enabled"
    const val UPDATE_SAMPLES = "min.hibernate.stats.logging.updates.samples"
    const val DELETE_LISTENERS_ENABLED = "min.hibernate.stats.logging.deletes.enabled"
    const val DELETE_SAMPLES = "min.hibernate.stats.logging.deletes.samples"
    const val INSERT_LISTENERS_ENABLED = "min.hibernate.stats.logging.inserts.enabled"
    const val INSERT_SAMPLES = "min.hibernate.stats.logging.inserts.samples"
}
