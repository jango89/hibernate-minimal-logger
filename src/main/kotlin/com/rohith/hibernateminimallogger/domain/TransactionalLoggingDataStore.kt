package com.rohith.hibernateminimallogger.domain

import com.rohith.hibernateminimallogger.properties.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val LOGGER: Logger = LoggerFactory.getLogger(TransactionalLoggingDataStore::class.java)


class TransactionalLoggingDataStore(
        internal var noOfCreationSamplesPerEntity: Int = 10,
        internal var noOfDeletionSamplesPerEntity: Int = 10,
        internal var noOfUpdationSamplesPerEntity: Int = 10,
        internal var canCreateUpdateListeners: Boolean = false,
        internal var canCreateDeleteListeners: Boolean = false,
        internal var canCreateInsertListeners: Boolean = false
) {

    fun reInitialize(props: Map<String, Any>): TransactionalLoggingDataStore {
        with(props) {
            canCreateUpdateListeners = updateListeners(this)
            canCreateDeleteListeners = deleteListeners(this)
            canCreateInsertListeners = insertListeners(this)
            noOfCreationSamplesPerEntity = createSamples(this)
            noOfDeletionSamplesPerEntity = deleteSamples(this)
            noOfUpdationSamplesPerEntity = updateSamples(this)
        }

        return this
    }

    private fun updateSamples(props: Map<String, Any>): Int =
            props.getOrDefault(Constants.UPDATE_SAMPLES, 10)
                    .also { LOGGER.info("Hibernate metrics update samples={}", it) }
                    as Int

    private fun createSamples(props: Map<String, Any>): Int =
            props.getOrDefault(Constants.INSERT_SAMPLES, 10)
                    .also { LOGGER.info("Hibernate metrics insert samples={}", it) }
                    as Int

    private fun deleteSamples(props: Map<String, Any>): Int =
            props.getOrDefault(Constants.DELETE_SAMPLES, 10)
                    .also { LOGGER.info("Hibernate metrics  delete samples={}", it) }
                    as Int

    private fun deleteListeners(props: Map<String, Any>): Boolean =
            props.getOrDefault(Constants.DELETE_LISTENERS_ENABLED, false)
                    .also { LOGGER.info("Hibernate metrics create delete listeners enabled={}", it) }
                    as Boolean

    private fun updateListeners(props: Map<String, Any>): Boolean =
            props.getOrDefault(Constants.UPDATE_LISTENERS_ENABLED, false)
                    .also { LOGGER.info("Hibernate metrics create update listeners enabled={}", it) }
                    as Boolean

    private fun insertListeners(props: Map<String, Any>): Boolean =
            props.getOrDefault(Constants.INSERT_LISTENERS_ENABLED, false)
                    .also { LOGGER.info("Hibernate metrics create insert listeners enabled={}", it) }
                    as Boolean
}
