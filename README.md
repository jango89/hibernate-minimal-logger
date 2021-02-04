# Hibernate Statistics logger

Library to enable minimal statistical recording of database queries.

## Motivation

Currently, it is impossible to *only* log or record the query and the time taken to execute them. 
Default implementation from hibernate is costly and it does a lot of things and not even sure how performant is this in a production system.

1. This library helps us to activate only the loggers based on things each individual or team wants.
2. Enable or disable logging of Statistics type.
3. Enable or disable storing and reporting of Statistics via Prometheus or similar time series database.
4. Easy customizations using system properties.
5. Injectable even on non-hibernate or non-spring projects by little tweaks.
6. Create alerts to notify about queries that needs to be improved.
7. Manually looking at the Database Reports on long running queries or running "Explain" command in the database. 

## Sample Use-cases

1. Developer need logs for sql queries with time taken to execute.
2. Developer need alert from Prometheus if there are queries taking more time than expected.
3. Developer needs prometheus metrics on how many rows being fetched for select queries.

## Usage

Dependency available [here](https://mvnrepository.com/artifact/com.github.jango89/hibernate-minimal-logger)

#### Non-Transactional Statistics

Configurable Properties.
1. "min.hibernate.stats.logging.enabled" - True/False (Enable logging)
2. "min.hibernate.stats.metrics.publish" - True/False (Enable storing in-memory and enabling it to be available via MetricsPublisher class)
3. "min.hibernate.stats.loggers" - QUERY_EXECUTION_TIME (Enables logging of query and the execution time)
4. "min.hibernate.stats.query.logging.min.executionTime.millis" - 250 (Minimum Milliseconds needed to report the metric).
5. "min.hibernate.stats.query.logging.min.rows" - 20 (Minimum number of rows fetched to report the metric).

###### In Hibernate

This property helps to instantiate our library.
"hibernate.stats.factory", new MinimalStatisticsFactory());

###### Without hibernate

Instantiate this "new MinimalStatisticsFactory()" and makes use of Classes in "com.rohith.hibernateminimallogger.loggers" package.

#### Transactional Statistics

Time taken for INSERTS, DELETES and UPDATES could also be recorded.

Configurable Properties.

1. "min.hibernate.stats.logging.updates.enabled" - Update listener is enabled, so time taken for updates are recorded.
3. "min.hibernate.stats.logging.deletes.enabled" - Delete listener is enabled, so time taken for deletes are recorded.
5. "min.hibernate.stats.logging.inserts.enabled" - Insert listener is enabled, so time taken for inserts are recorded.

"min.hibernate.stats.logging.updates.samples"
"min.hibernate.stats.logging.inserts.samples"
"min.hibernate.stats.logging.deletes.samples"

=> Limit how many samples could be collected to compare instead of recording every transaction and wasting the resources. 

This will then run comparisons on those collected samples based on each thread id.

However of-course once the metric is reported to prometheus via MetricPublisher, the collected data is cleared and again recorded.

###### In Hibernate

This property helps to instantiate our library.
"hibernate.integrator_provider", new ListenerIntegrator(hibernateProperties)

## Report to graph database or Prometheus

1. Enable saving data by enabling "min.hibernate.stats.metrics.publish" property via system property.
2. And scrape information from [methods here](https://github.com/jango89/hibernate-minimal-logger/blob/main/src/main/kotlin/com/rohith/hibernateminimallogger/metrics/MetricPublisher.kt) based on necessity.

## Known Pitfalls

Latest hibernate versions may need changes in this (file)[https://github.com/jango89/hibernate-minimal-logger/blob/main/src/main/kotlin/com/rohith/hibernateminimallogger/hibernateenablers/StatisticsIntegrator.kt]. Since it implements the hibernate provided Interface.


## Integration Docs

[Available here](https://github.com/jango89/hibernate-minimal-logger/wiki)

