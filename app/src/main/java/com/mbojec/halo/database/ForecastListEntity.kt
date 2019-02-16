package com.mbojec.halo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
class ForecastListEntity(@field:PrimaryKey var rowId: Int,
                         var cityId: Int)