package com.mbojec.halo.utils

import androidx.core.content.res.ResourcesCompat
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.CurrentForecast
import com.mbojec.halo.model.ForecastInfo
import com.mbojec.halo.model.LongTermForecast
import com.mbojec.halo.model.ShortTermForecast

object WeatherDataConverter {

    fun createCurrentForecast(forecastEntity: ForecastEntity, application: HaloApplication): CurrentForecast{
        return CurrentForecast(
            forecastEntity.cityId,
            forecastEntity.feature.text?: "--",
            DataUtils.formatTemperature(application.applicationContext, forecastEntity.forecast.currently?.temperature),
            forecastEntity.forecast.currently?.summary?: "--",
            capitalizeSpelling(DataUtils.getCurrentDayName(forecastEntity.forecast.currently?.time, forecastEntity.forecast.timezone, application.applicationContext)),
            DataUtils.getImageResourceForWeatherCondition(forecastEntity.forecast.currently?.icon),
            DataUtils.getFormattedCurrentHour(forecastEntity.forecast.timezone, application.applicationContext),
            getProperBackgroundColor(forecastEntity, application))
    }

    fun createShortTermForecastList(forecastEntity: ForecastEntity, application: HaloApplication): List<ShortTermForecast>{
        val resultList: ArrayList<ShortTermForecast> = ArrayList()
        val shortTermForecastList = forecastEntity.forecast.hourly?.dataHourlies
        val context = application.applicationContext
        for (i in 0..23){
            resultList.add(ShortTermForecast(
                DataUtils.getFormattedHour(shortTermForecastList?.get(i)?.time, forecastEntity.forecast.timezone, application.applicationContext),
                DataUtils.formatTemperature(context, shortTermForecastList?.get(i)?.temperature),
                DataUtils.getImageResourceForWeatherCondition(shortTermForecastList?.get(i)?.icon),
                getProperBackgroundColor(forecastEntity, application)))
        }
        return resultList.toList()
    }

    fun createLongTermForecastList(forecastEntity: ForecastEntity, application: HaloApplication): List<LongTermForecast>{
        val resultList: ArrayList<LongTermForecast> = ArrayList()
        val longTermForecastList = forecastEntity.forecast.daily?.dataDailies
        val context = application.applicationContext
        for(i in 0..7){
            resultList.add(LongTermForecast(
                capitalizeSpelling(DataUtils.getDayName(longTermForecastList?.get(i)?.time, forecastEntity.forecast.timezone, application.applicationContext)),
                DataUtils.getImageResourceForWeatherCondition(longTermForecastList?.get(i)?.icon),
                DataUtils.formatTemperature(context, longTermForecastList?.get(i)?.temperatureHigh),
                DataUtils.formatTemperature(context, longTermForecastList?.get(i)?.temperatureLow),
                getProperBackgroundColor(forecastEntity, application)))
        }
        return resultList.toList()
    }

    fun createForecastInfo(forecastEntity: ForecastEntity, application: HaloApplication): ForecastInfo {
        return  ForecastInfo(forecastEntity.cityId,
            DataUtils.getFormattedPressure(application.applicationContext, forecastEntity.forecast.currently?.pressure),
            DataUtils.getFormattedHumidity(application.applicationContext, forecastEntity.forecast.currently?.humidity),
            DataUtils.getFormattedWindSpeed(application.applicationContext, forecastEntity.forecast.currently?.windSpeed),
            DataUtils.getFormattedWindDirection(application.applicationContext, forecastEntity.forecast.currently?.windBearing?.toDouble()),
            DataUtils.getFormattedHour(forecastEntity.forecast.daily?.dataDailies?.get(0)?.sunsetTime, forecastEntity.forecast.timezone, application.applicationContext ),
            DataUtils.getFormattedHour(forecastEntity.forecast.daily?.dataDailies?.get(0)?.sunriseTime, forecastEntity.forecast.timezone, application.applicationContext),
            DataUtils.getFormattedRainVolume(application.applicationContext, forecastEntity.forecast.currently?.precipIntensity),
            DataUtils.getFormattedCloudiness(application.applicationContext, (forecastEntity.forecast.currently?.cloudCover!! * 100).toInt()),
            DataUtils.getFormattedUvIndex(application.applicationContext, forecastEntity.forecast.currently?.uvIndex?.toDouble()),
            getProperBackgroundColor(forecastEntity, application))
    }

    private fun capitalizeSpelling(cityName:String): String{
        return if (cityName.contains(" ")){
            val splitCityName = cityName.split(" ")
            val builder = StringBuilder()
            for (singleWord in splitCityName){
                val capWord = singleWord.substring(0,1).toUpperCase() + singleWord.substring(1) + " "
                builder.append(capWord)
            }
            builder.toString()
        } else {
            cityName.substring(0,1).toUpperCase() + cityName.substring(1)
        }
    }

    fun getProperBackgroundColor(forecastEntity: ForecastEntity, application: HaloApplication): Int {
        return if (DataUtils.isItDay(forecastEntity)){
            ResourcesCompat.getColor(application.applicationContext.resources, R.color.dayColor, null)

        } else {
            ResourcesCompat.getColor(application.applicationContext.resources, R.color.nightColor, null)
        }
    }
}