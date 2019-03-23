package com.tragicfruit.kindweather.utils

object Converter {

    fun none(value: Double) = value

    object Default : Measurement {
        override fun toMetric(value: Double) = none(value)
        override fun toImperial(value: Double) = none(value)
        override fun fromMetric(value: Double) = none(value)
        override fun fromImperial(value: Double) = none(value)
    }

    /**
     * Degrees Celsius
     */
    object Temperature : Measurement {
        override fun toMetric(value: Double) = none(value)
        override fun toImperial(value: Double) = value * 1.8 + 32
        override fun fromMetric(value: Double) = none(value)
        override fun fromImperial(value: Double) = value - 32 / 1.8
    }

    /**
     * Millimeters per hour
     */
    object Precipitation : Measurement {
        override fun toMetric(value: Double) = none(value)
        override fun toImperial(value: Double) = value / 25.4
        override fun fromMetric(value: Double) = none(value)
        override fun fromImperial(value: Double) = value * 25.4
    }

    /**
     * Probability
     */
    object Probability : Measurement {
        override fun toMetric(value: Double) = value * 100
        override fun toImperial(value: Double) = value * 100
        override fun fromMetric(value: Double) = value / 100
        override fun fromImperial(value: Double) = value / 100
    }

    /**
     * Proportion
     */
    object Humidity : Measurement {
        override fun toMetric(value: Double) = value * 100
        override fun toImperial(value: Double) = value * 100
        override fun fromMetric(value: Double) = value / 100
        override fun fromImperial(value: Double) = value / 100
    }

    /**
     * Meters per second
     */
    object WindSpeed : Measurement {
        override fun toMetric(value: Double) = value * 3.6
        override fun toImperial(value: Double) = value * 3.6 / 1.609344
        override fun fromMetric(value: Double) = value / 3.6
        override fun fromImperial(value: Double) = value * 1.609344 / 3.6
    }

    interface Measurement {
        fun toMetric(value: Double): Double
        fun toImperial(value: Double): Double
        fun fromMetric(value: Double): Double
        fun fromImperial(value: Double): Double
    }

}