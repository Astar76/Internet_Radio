package com.astar.myapplication.data

import com.astar.myapplication.data.local.model.RadioEntity
import com.astar.myapplication.data.remote.model.RadioPoint
import com.astar.myapplication.data.remote.model.Radios
import com.astar.myapplication.domain.model.Radio

fun RadioPoint.toRadio(): Radio {
    return Radio(
        id = this.channelId,
        name = this.name,
        stream = this.streamingUrl
    )
}

fun RadioEntity.toRadio(): Radio {
    return Radio(
        id = this.channelId,
        name = this.name,
        stream = this.streamUrl
    )
}

fun Radios.toRadioList(): List<Radio> {
    return this.radioPoints.map {
        Radio(
            id = it.channelId,
            name = it.name,
            stream = it.streamingUrl
        )
    }
}

fun Radio.toRadioEntity(): RadioEntity {
    return RadioEntity(
        channelId = this.id,
        name = this.name,
        streamUrl = this.stream
    )
}