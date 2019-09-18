package com.xinh.location.param

import java.util.concurrent.TimeUnit

data class LocationTime(val time: Long, val timeUnit: TimeUnit) {
    /**
     * Factory methods
     */
    companion object {
        /**
         * @return one day [LocationTime]
         */
        @JvmStatic
        fun oneDay() = LocationTime(1L, TimeUnit.DAYS)

        /**
         * @return one hour [LocationTime]
         */
        @JvmStatic
        fun oneHour() = LocationTime(1L, TimeUnit.HOURS)
    }
}