package com.gautham.mafia.Navigation

import kotlinx.serialization.Serializable
import kotlin.math.truncate
interface NavObject
@Serializable
object Home:NavObject{
    val ratio:Float = -2f
}
@Serializable
object CreateRoom:NavObject
{
    val ratio:Float = -20f
}
@Serializable
object JoinRoom:NavObject
{
    val ratio:Float = 3f
}
@Serializable
object Searching:NavObject{

    val ratio:Float = 20f
    var roomId:String = ""

}
@Serializable
object RoomFound:NavObject{

    val ratio:Float = -6f
    var roomId:String = ""
}
@Serializable
object Lobby:NavObject{

    val ratio:Float = -8f
    var isHost = true

}
@Serializable
object Loading:NavObject{

    val ratio:Float = 20f


}
@Serializable
object ProfileChange:NavObject
{
    val ratio:Float = 2f
}
@Serializable
object RoleReveal:NavObject{
    val ratio:Float = 20f
}
@Serializable
object MainGame:NavObject
@Serializable
object splashScreen
@Serializable
object GAMEOVER:NavObject
{
    val ratio:Float = 20f

}