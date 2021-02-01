package com.rohith.hibernateminimallogger.metrics

import java.lang.ref.WeakReference
import java.util.*


object MetricHolder {

    internal val queryWithExecutionTime = WeakHashMap<String, Long>()
    internal val queryWithRows = WeakHashMap<String, Int>()

    internal val insertEntityWithThreadAndTimeReference =
            WeakHashMap<String, WeakHashMap<Long, WeakReference<TimeStats>>>()

    internal val updateEntityWithThreadAndTimeReference =
            WeakHashMap<String, WeakHashMap<Long, WeakReference<TimeStats>>>()

    internal val deleteEntityWithThreadAndTimeReference =
            WeakHashMap<String, WeakHashMap<Long, WeakReference<TimeStats>>>()

    data class TimeStats(var start: Long = 0, var finish: Long = 0)
}
