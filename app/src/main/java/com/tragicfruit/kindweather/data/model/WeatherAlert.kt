package com.tragicfruit.kindweather.data.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tragicfruit.kindweather.R

@Entity(tableName = "alerts")
data class WeatherAlert(
    @PrimaryKey val id: String,
    val alertType: WeatherAlertType,
    val priority: Int,
    var enabled: Boolean = true
)

data class WeatherAlertWithParams(
    @Embedded val alert: WeatherAlert,
    @Relation(
        parentColumn = "id",
        entityColumn = "alertId"
    )
    val params: List<WeatherAlertParam>
)

enum class WeatherAlertType(
    @StringRes val title: Int,
    @StringRes val shortTitle: Int,
    @ColorRes val color: Int,
    @DrawableRes val image: Int
) {

    Umbrella(
        title = R.string.alert_umbrella,
        shortTitle = R.string.alert_umbrella_short,
        color = R.color.alert_umbrella,
        image = R.drawable.umbrella
    ),

    Jacket(
        title = R.string.alert_jacket,
        shortTitle = R.string.alert_jacket_short,
        color = R.color.alert_jacket,
        image = R.drawable.warm_clothing
    ),

    TShirt(
        title = R.string.alert_tshirt,
        shortTitle = R.string.alert_tshirt_short,
        color = R.color.alert_tshirt,
        image = R.drawable.tshirt_shorts
    ),

    Sunscreen(
        title = R.string.alert_sunscreen,
        shortTitle = R.string.alert_sunscreen_short,
        color = R.color.alert_sunscreen,
        image = R.drawable.sunscreen
    ),

    RainJacket(
        title = R.string.alert_rain_jacket,
        shortTitle = R.string.alert_rain_jacket_short,
        color = R.color.alert_rain_jacket,
        image = R.drawable.rain_jacket
    ),

    ThickJacket(
        title = R.string.alert_thick_jacket,
        shortTitle = R.string.alert_thick_jacket_short,
        color = R.color.alert_thick_jacket,
        image = R.drawable.winter_clothing
    )
}
