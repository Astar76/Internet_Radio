package com.astar.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class Radios(
    @SerializedName("radios") val radioPoints: List<RadioPoint>
)

data class RadioPoint(
    @SerializedName("image_url") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("uri") val streamingUrl: String,
    @SerializedName("channel_id") val channelId: Int,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("genre") val genre: String
)