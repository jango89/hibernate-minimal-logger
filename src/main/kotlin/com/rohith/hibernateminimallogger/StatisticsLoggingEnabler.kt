package com.rohith.hibernateminimallogger

import com.rohith.hibernateminimallogger.domain.DataStore
import com.rohith.hibernateminimallogger.loggers.QueryRowStatisticsLogger
import com.rohith.hibernateminimallogger.loggers.QueryTimeStatisticsLogger
import org.hibernate.stat.*
import org.hibernate.stat.spi.StatisticsImplementor

class StatisticsLoggingEnabler(private val dataStore: DataStore) : StatisticsImplementor {

    override fun queryExecuted(sql: String, rows: Int, timeInMs: Long) {
        QueryTimeStatisticsLogger().execute(sql, timeInMs, dataStore)
        QueryRowStatisticsLogger().execute(sql, rows, dataStore)
    }

    override fun isStatisticsEnabled(): Boolean {
        return true;
    }

    override fun setStatisticsEnabled(p0: Boolean) {
    }

    override fun clear() {
    }

    override fun logSummary() {
    }

    override fun getEntityStatistics(p0: String?): EntityStatistics? {
        return null
    }

    override fun getCollectionStatistics(p0: String?): CollectionStatistics? {
        return null
    }

    override fun getQueryStatistics(p0: String?): QueryStatistics? {
        return null
    }

    override fun getEntityDeleteCount(): Long {
        return 0
    }

    override fun getEntityInsertCount(): Long {
        return 0
    }

    override fun getEntityLoadCount(): Long {
        return 0
    }

    override fun getEntityFetchCount(): Long {
        return 0
    }

    override fun getEntityUpdateCount(): Long {
        return 0
    }

    override fun getQueryExecutionCount(): Long {
        return 0
    }

    override fun getQueryExecutionMaxTime(): Long {
        return 0
    }

    override fun getQueryExecutionMaxTimeQueryString(): String {
        return "0"
    }

    override fun getQueryCacheHitCount(): Long {
        return 0
    }

    override fun getQueryCacheMissCount(): Long {
        return 0
    }

    override fun getQueryCachePutCount(): Long {
        return 0
    }

    override fun getNaturalIdQueryExecutionCount(): Long {
        return 0
    }

    override fun getNaturalIdQueryExecutionMaxTime(): Long {
        return 0
    }

    override fun getNaturalIdQueryExecutionMaxTimeRegion(): String {
        return "0"
    }

    override fun getNaturalIdCacheHitCount(): Long {
        return 0
    }

    override fun getNaturalIdCacheMissCount(): Long {
        return 0
    }

    override fun getNaturalIdCachePutCount(): Long {
        return 0
    }

    override fun getUpdateTimestampsCacheHitCount(): Long {
        return 0
    }

    override fun getUpdateTimestampsCacheMissCount(): Long {
        return 0
    }

    override fun getUpdateTimestampsCachePutCount(): Long {
        return 0
    }

    override fun getFlushCount(): Long {
        return 0
    }

    override fun getConnectCount(): Long {
        return 0
    }

    override fun getSecondLevelCacheHitCount(): Long {
        return 0
    }

    override fun getSecondLevelCacheMissCount(): Long {
        return 0
    }

    override fun getSecondLevelCachePutCount(): Long {
        return 0
    }

    override fun getSessionCloseCount(): Long {
        return 0
    }

    override fun getSessionOpenCount(): Long {
        return 0
    }

    override fun getCollectionLoadCount(): Long {
        return 0
    }

    override fun getCollectionFetchCount(): Long {
        return 0
    }

    override fun getCollectionUpdateCount(): Long {
        return 0
    }

    override fun getCollectionRemoveCount(): Long {
        return 0
    }

    override fun getCollectionRecreateCount(): Long {
        return 0
    }

    override fun getStartTime(): Long {
        return 0
    }

    override fun getQueries(): Array<String> {
        return emptyArray()
    }

    override fun getEntityNames(): Array<String> {
        return emptyArray()
    }

    override fun getCollectionRoleNames(): Array<String> {
        return emptyArray()
    }

    override fun getSecondLevelCacheRegionNames(): Array<String> {
        return emptyArray()
    }

    override fun getSuccessfulTransactionCount(): Long {
        return 0
    }

    override fun getTransactionCount(): Long {
        return 0
    }

    override fun getPrepareStatementCount(): Long {
        return 0
    }

    override fun getCloseStatementCount(): Long {
        return 0
    }

    override fun getOptimisticFailureCount(): Long {
        return 0
    }

    override fun getSecondLevelCacheStatistics(p0: String?): SecondLevelCacheStatistics? {
        return null
    }

    override fun getNaturalIdCacheStatistics(p0: String?): NaturalIdCacheStatistics? {
        return null
    }

    override fun openSession() {

    }

    override fun closeSession() {

    }

    override fun flush() {

    }

    override fun connect() {

    }

    override fun prepareStatement() {

    }

    override fun closeStatement() {

    }

    override fun endTransaction(p0: Boolean) {

    }

    override fun loadEntity(p0: String?) {

    }

    override fun fetchEntity(p0: String?) {

    }

    override fun updateEntity(p0: String?) {

    }

    override fun insertEntity(p0: String?) {

    }

    override fun deleteEntity(p0: String?) {

    }

    override fun optimisticFailure(p0: String?) {

    }

    override fun loadCollection(p0: String?) {

    }

    override fun fetchCollection(p0: String?) {

    }

    override fun updateCollection(p0: String?) {

    }

    override fun recreateCollection(p0: String?) {

    }

    override fun removeCollection(p0: String?) {

    }

    override fun secondLevelCachePut(regionName: String?) {

    }

    override fun secondLevelCacheHit(regionName: String?) {

    }

    override fun secondLevelCacheMiss(regionName: String?) {

    }

    override fun naturalIdCachePut(regionName: String?) {

    }

    override fun naturalIdCacheHit(regionName: String?) {
    }

    override fun naturalIdCacheMiss(regionName: String?) {
    }

    override fun naturalIdQueryExecuted(p0: String?, p1: Long) {
    }

    override fun queryCachePut(p0: String?, p1: String?) {
    }

    override fun queryCacheHit(p0: String?, p1: String?) {
    }

    override fun queryCacheMiss(p0: String?, p1: String?) {
    }

    override fun updateTimestampsCacheHit() {
    }

    override fun updateTimestampsCacheMiss() {
    }

    override fun updateTimestampsCachePut() {
    }
}
