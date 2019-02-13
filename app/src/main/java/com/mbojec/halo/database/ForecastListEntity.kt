package com.mbojec.halo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mbojec.halo.model.ForecastListItem

@Entity(tableName = "list")
class ForecastListEntity(@field:PrimaryKey var id: Int,
                         @field:TypeConverters(ForecastListConverter::class) var forecastList: List<ForecastListItem>)