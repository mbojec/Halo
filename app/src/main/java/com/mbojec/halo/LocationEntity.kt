package com.mbojec.halo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
class LocationEntity (@field:PrimaryKey var id: Int, var longitude: Double, var latitude: Double, var updateTime: Long)