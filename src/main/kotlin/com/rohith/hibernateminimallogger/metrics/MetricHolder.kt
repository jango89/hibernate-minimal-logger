package com.rohith.hibernateminimallogger.metrics

import java.util.*


internal object MetricHolder {

    internal val queryWithExecutionTime = WeakHashMap<String, Long>()
    internal val queryWithRows = WeakHashMap<String, Int>()

}
