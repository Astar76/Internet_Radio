package com.astar.myapplication.data.remote

import com.astar.myapplication.data.RadioData
import com.google.gson.annotations.SerializedName

data class Radios(
    @SerializedName("radios") val radioPoints: List<RadioPoint>
) {
    fun toRadioData(): List<RadioData> = radioPoints.map {
        RadioData(name = it.name, stream = it.streamingUrl)
    }
}

data class RadioPoint(
    @SerializedName("image_url") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("uri") val streamingUrl: String,
    @SerializedName("channel_id") val channelId: Int,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("genre") val genre: String
)