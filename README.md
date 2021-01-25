# Hibernate Statistics logger

Library to enable minimal statistical recording of database queries.

## Motivation

Currently, it is impossible to *only* log or record the query and the time taken to execute them. 
Default implementation from hibernate is costly and it does a lot of things and not even sure how performant is this in a production system.

1. This library helps us to activate only the loggers based on things each individual or team wants.
2. Enable or disable logging of Statistics type.
3. Enable or disable storing and reporting of Statistics.
4. Easy customizations using system properties.
5. Injectable even on non-hibernate or non-spring projects by little tweaks.
6. Statistics reporting will help the data to be sent to Prometheus or similar databases and create alerts to notify whether there are queries that needs to be improved.

## Sample Use-cases

1. Developer need logs for sql queries with time taken to execute.
2. Developer need alert from Prometheus if there are queries taking more time than expected.
3. Developer needs prometheus metrics on how many rows being fetched for select queries.

## Usage

Dependency available [here](https://mvnrepository.com/artifact/com.github.jango89/hibernate-minimal-logger)
```
<dependency>
  <groupId>com.github.jango89</groupId>
  <artifactId>hibernate-minimal-logger</artifactId>
  <version>0.0.2</version>
</dependency>
```

Configurable Properties.
1. "min.hibernate.stats.logging.enabled" - True/False (Enable logging)
2. "min.hibernate.stats.metrics.publish" - True/False (Enable storing in-memory and enabling it to be available via MetricsPublisher class)
3. "min.hibernate.stats.loggers" - QUERY_EXECUTION_TIME (Enables logging of query and the execution time)
4. "min.hibernate.stats.query.logging.min.executionTime.millis" - 250 (Minimum Milliseconds needed to report the metric).
5. "min.hibernate.stats.query.logging.min.rows" - 20 (Minimum number of rows fetched to report the metric).



### In Hibernate

This property helps to instantiate our library.
"hibernate.stats.factory", new MinimalStatisticsFactory());

### Without hibernate

Instantiate this "new MinimalStatisticsFactory()" and makes use of Classes in "com.rohith.hibernateminimallogger.loggers" package.

## Report to graph database or Prometheus

1. Enable saving data by enabling "min.hibernate.stats.metrics.publish" property via system property.
2. And scrape information from [methods here](https://github.com/jango89/hibernate-minimal-logger/blob/main/src/main/kotlin/com/rohith/hibernateminimallogger/metrics/MetricPublisher.kt) based on necessity.
