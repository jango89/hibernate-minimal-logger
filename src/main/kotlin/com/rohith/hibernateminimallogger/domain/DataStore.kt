package com.rohith.hibernateminimallogger.domain

import com.rohith.hibernateminimallogger.properties.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(DataStore::class.java)


class DataStore(
        private var isLoggingEnabled: Boolean = false,
        internal var canReportMetric: Boolean = false,
        internal var minTimeInMilliSecondToReportMetric: Int = 250,
        internal var minRowsToReportMetric: Int = 20,
        private var enabledLoggers: List<StatisticsLoggerType> = listOf()
) {

    fun reInitialize(props: Map<String, Any>): DataStore {
        with(props) {
            isLoggingEnabled = loggingEnabled(this)
            canReportMetric = reportMetricsEnabled(this)
            enabledLoggers = loggersEnabled(this)
            minTimeInMilliSecondToReportMetric = minQueryTimeForQueryExecutionReporting(this)
            minRowsToReportMetric = minRowsForQueryExecutionReporting(this)
        }

        return this
    }

    fun isEnabled(loggerType: StatisticsLoggerType) = enabledLoggers.contains(loggerType)

    fun eligibleToLog() = isLoggingEnabled

    private fun loggersEnabled(props: Map<String, Any>): List<StatisticsLoggerType> {
        if (props.containsKey(Constants.LOGGERS)) {

            val loggers = props[Constants.LOGGERS].toString().trim()
            if (loggers.isNotEmpty()) {
                val enabledLoggers = loggers.split(",")
                return enabledLoggers.map { StatisticsLoggerType.valueOf(it.trim()) }
                        .also { LOGGER.info("Hibernate metrics loggers enabled={}", it) }
            }
        }
        return listOf(StatisticsLoggerType.QUERY_EXECUTION_TIME, StatisticsLoggerType.QUERY_ROWS_COUNT)
                .also { LOGGER.info("Hibernate metrics loggers enabled={}", it) }
    }

    private fun reportMetricsEnabled(props: Map<String, Any>): Boolean =
            props.getOrDefault(Constants.METRICS_REPORT_ENABLED, false)
                    .also { LOGGER.info("Hibernate metrics published enabled={}", it) }
                    as Boolean

    private fun loggingEnabled(props: Map<String, Any>): Boolean =
            props.getOrDefault(Constants.LOGGING_ENABLED, true)
                    .also { LOGGER.info("Hibernate metrics logging enabled={}", it) }
                    as Boolean

    private fun minQueryTimeForQueryExecutionReporting(props: Map<String, Any>): Int =
            props.getOrDefault(Constants.QUERY_MIN_EXECUTION_TIME_IN_MS, 250)
                    .also { LOGGER.info("Hibernate metrics min time for query execution in ms={}", it) }
                    as Int

    private fun minRowsForQueryExecutionReporting(props: Map<String, Any>): Int =
            props.getOrDefault(Constants.QUERY_MIN_ROWS, 20)
                    .also { LOGGER.info("Hibernate metrics min rows for query={}", it) }
                    as Int

}
