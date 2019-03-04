package com.mbojec.halo.utils

import android.content.Context
import android.preference.PreferenceManager
import com.mbojec.halo.R
import com.mbojec.halo.database.entity.ForecastEntity
import java.text.SimpleDateFormat
import java.util.*

object DataUtils {

    private fun isMetric(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(context.getString(R.string.pref_units_key),
            context.getString(R.string.pref_units_metric)) == context.getString(R.string.pref_units_metric)
    }

    private fun isLocal(context: Context, timeZone: String): java.util.TimeZone {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return if(prefs.getBoolean(context.getString(R.string.pref_time_zone_key), true)){
            java.util.TimeZone.getTimeZone(timeZone)
        } else {
            java.util.TimeZone.getDefault()
        }
    }


    fun formatTemperature(context: Context, temperature: Double?): String {
        temperature?.let {
            var finaleTemperature = temperature
            if (!isMetric(context)) {
                finaleTemperature = temperature * 1.8 + 32
            }
            return String.format(context.getString(R.string.format_temperature), finaleTemperature)
        }?: return "--"
    }

    fun getDayName(dateInMillis: Long?, timeZone: String?, context: Context): String {
        dateInMillis?.let {
            timeZone?.let {
                val dayFormat = SimpleDateFormat("EE, dd MMMM", Locale.getDefault())
                dayFormat.timeZone = isLocal(context, timeZone)
                return dayFormat.format(dateInMillis * 1000)
            } ?: return "--"
        } ?: return "--"
    }

    fun getCurrentDayName(timeInMillis: Long?, timeZone: String?, context: Context): String {
        timeInMillis?.let {
            timeZone?.let {
                val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
                dayFormat.timeZone = isLocal(context, timeZone)
                return dayFormat.format(timeInMillis * 1000)
            }?: return "--"
        }?: return "--"
    }

    fun getFormattedHour(timeInMillis: Long?, timeZone: String?, context: Context): String {
        timeInMillis?.let {
            timeZone?.let {
                val timeDayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                timeDayFormat.timeZone = isLocal(context, timeZone)
                return timeDayFormat.format(timeInMillis * 1000)
            } ?: return "--"
        } ?: return "--"
    }

    fun getFormattedCurrentHour(timeZone: String?, context: Context): String {
        timeZone?.let {
            val timeDayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            timeDayFormat.timeZone= isLocal(context, timeZone)
            return timeDayFormat.format(getCurrentTime())
        } ?: return "--"
    }

    fun getFormattedUvIndex(context: Context, uvValue: Double?): String {
        uvValue?.let {
            return String.format(context.getString(R.string.format_uv_index), uvValue)
        } ?: return "--"
    }

    fun isItDay(forecastEntity: ForecastEntity): Boolean {
        val gmtOffset = forecastEntity.forecast.offset!! * 3600000
        val sunriseTime = (forecastEntity.forecast.daily!!.dataDailies!![0].sunriseTime!! * 1000) + gmtOffset.toLong()
        val sunsetTime = (forecastEntity.forecast.daily!!.dataDailies!![0].sunsetTime!! * 1000) + gmtOffset.toLong()
        val currentTime = getCurrentTime() + gmtOffset.toLong()
        return currentTime > sunriseTime && currentTime < sunsetTime

    }

    fun getFormattedWindSpeed(context: Context, windSpeed: Double?): String {
        windSpeed?.let {
            var finalWindSpreed = windSpeed
            val windFormat: Int
            if (DataUtils.isMetric(context)) {
                windFormat = R.string.format_wind_kmh
            } else {
                windFormat = R.string.format_wind_mph
                finalWindSpreed *= .621371192237334f
            }
            return String.format(context.getString(windFormat), finalWindSpreed)
        } ?: return "--"
    }

    fun getFormattedWindDirection(context: Context, degrees: Double?): String {
        degrees?.let {
            val direction = "Unknown"
            return when(Math.abs(degrees)){
                in 0.0..22.4 -> context.getString(R.string.n)
                in 22.5..67.4 -> context.getString(R.string.ne)
                in 67.5..112.4 -> context.getString(R.string.e)
                in 112.5..157.4 -> context.getString(R.string.se)
                in 157.5..202.4 -> context.getString(R.string.s)
                in 202.5..247.4 -> context.getString(R.string.sw)
                in 247.5..292.4 -> context.getString(R.string.w)
                in 292.5..337.4 -> context.getString(R.string.nw)
                in 337.5..360.0 -> context.getString(R.string.n)
                else -> direction
            }
        }?: return "--"
    }

    fun getFormattedPressure(context: Context, pressure: Double?): String {
        pressure?.let {
            val pressureFormat = R.string.format_pressure
            return String.format(context.getString(pressureFormat), pressure)
        }?: return "--"
    }

    fun getFormattedHumidity(context: Context, humidity: Double?): String {
        humidity?.let {
            val humidityFormat = R.string.format_humidity
            return String.format(context.getString(humidityFormat), humidity)
        }?: return "--"
    }

    fun getFormattedRainVolume(context: Context, rainVolume: Double?): String {
        rainVolume?.let {
            val rainVolumeFormat = R.string.format_rain
            return String.format(context.getString(rainVolumeFormat), rainVolume)
        }?: return "--"
    }

    fun getFormattedCloudiness(context: Context, cloudiness: Int?): String {
        cloudiness?.let {
            val cloudinessFormat = R.string.format_cloudiness
            return String.format(context.getString(cloudinessFormat), cloudiness)
        }?: return "--"
    }

    fun getImageResourceForWeatherCondition(weatherId: String?): Int {
        return when (weatherId) {
            "clear-day" -> R.drawable.clear_day
            "clear-night" -> R.drawable.clear_night
            "rain" -> R.drawable.rain
            "snow" -> R.drawable.snow
            "sleet" -> R.drawable.sleet
            "wind" -> R.drawable.wind
            "fog" -> R.drawable.fog
            "cloudy" -> R.drawable.cloudy
            "partly-cloudy-day" -> R.drawable.partly_cloudy_day
            "partly-cloudy-night" -> R.drawable.partly_cloudy_night
            "hail" -> R.drawable.hail
            "thunderstorm" -> R.drawable.thunderstorm
            "tornado" -> R.drawable.thunderstorm
            else -> R.drawable.clear_day
        }
    }

    fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

}