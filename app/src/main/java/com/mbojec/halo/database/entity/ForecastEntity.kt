package com.mbojec.halo.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.model.Forecast

@Entity(tableName = "forecast")
class ForecastEntity(@field:PrimaryKey var cityId: Long,
                     @field:Embedded var feature: SearchCityList.Feature,
                     @field:Embedded var forecast: Forecast)