package com.mafia2.data

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.gautham.mafia.R
import kotlinx.serialization.Serializable

enum class Phase{
    GAMESTARTING,
    DAY,
    NIGHT,
    VOTING,
    GAMEOVER

}
enum class Role(val Icon:Int){

    DETECTIVE(R.drawable.detective),
    MAFIA(R.drawable.mafia),
    DOCTOR(R.drawable.doctor),
    CITIZEN(R.drawable.villager),
   GOD(R.drawable.ic_launcher_foreground)

}
@Serializable
data class gameSettings(
    var totalP:Int = defaultSettings().totalP,
    var noGod:Int= defaultSettings().noGod,
    var noDetective :Int = defaultSettings().noDetective,
    var noMafia:Int = defaultSettings().noMafia,
    var noDoctor:Int = defaultSettings().noDoctor,
    var noCitizen:Int = defaultSettings().noCitizen,

    ){

    companion object{

        fun defaultSettings():gameSettings
        {
         return  gameSettings(
             noGod=0,
             noDetective=1,
             noMafia=1,
             noDoctor=1,
             noCitizen=2,
             totalP = 5
         )
        }
        }
}
data class RoleInfo(
    val role:Role,
    var no:Int
    )
fun gameSettings.toList():List<RoleInfo>{
    return listOf(
        RoleInfo(Role.CITIZEN,noCitizen),
        RoleInfo(Role.DOCTOR,noDoctor),
        RoleInfo(Role.MAFIA,noMafia),
        RoleInfo(Role.DETECTIVE,noDetective),
        RoleInfo(Role.GOD,noGod),



    )




}
fun toGameSettings(list:List<RoleInfo>):gameSettings{
    return gameSettings(
        noCitizen = list[0].no,
        noDoctor = list[1].no,
        noMafia = list[2].no,
        noDetective = list[3].no,

    )


}
@Serializable
data class GameState(
    val day: Int = 0,
    val currentPhase: Phase = Phase.GAMESTARTING,
    val currentRoleTurn: Role? = null,
    var players: List<Player> = emptyList(),
    val playersNeeded: Int = 5,
    val RolesMap: Map<Int, Role> = emptyMap(),
    val gameSettings: gameSettings = gameSettings(),
    val toBeKilled: Int? = null,
    val toBeSaved: Int? = null,
    val toSuspect: Int? = null,
    val isSuspect: Boolean = false,
    var isVoting: Boolean = false,
    val votersList: Map<Int, Int> = emptyMap(),
    val isGameOver: Boolean = false,
    val isWinnerMafia: Boolean = false,
    val id: String,
    val host :Int?=null


){


}

@Serializable
data class DoAction(

    val player: Player,
    val affectedPlayer:Int,

    )