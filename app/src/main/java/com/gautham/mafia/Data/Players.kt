package com.mafia2.data

import com.gautham.mafia.Data.Avatar
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    var isAlive: Boolean = true,
    val avatar: Avatar
)
@Serializable
data class PlayerDet( val name: String,val avatar: Avatar=Avatar())
