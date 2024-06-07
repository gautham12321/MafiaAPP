package com.gautham.mafia.Navigation

import kotlinx.serialization.Serializable
import kotlin.math.truncate

@Serializable
object Home{
    val ratio:Float = -2f
}
@Serializable
object CreateRoom
{
    val ratio:Float = -13f
}
@Serializable
object JoinRoom
{
    val ratio:Float = 5f
}
@Serializable
object RoomFound{

    val ratio:Float = -6f
}
@Serializable
object Lobby{

    val ratio:Float = -8f
    var isHost = true

}
@Serializable
object Loading{

    val ratio:Float = -12f


}
@Serializable
object ProfileChange
{
    val ratio:Float = 2f
}