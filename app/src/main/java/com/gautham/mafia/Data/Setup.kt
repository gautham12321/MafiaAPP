package com.gautham.mafia.Data

import com.mafia2.data.Player
import kotlinx.serialization.Serializable

@Serializable
data class Setup(val roomFound:Boolean=false,val hostDetails: Player?=null,val playerDetails: Player?=null,val searching:Boolean=false)
