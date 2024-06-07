package com.mafia2.data

import kotlinx.serialization.Serializable

@Serializable
data class Request<U>(val roomId:String, val information:U)