package com.gautham.mafia.Data

import kotlinx.serialization.Serializable
enum class AudioToPlay{

    VILLAGERCLOSE,
    WAKE_NODEATH,
    WAKE_WITHDEATH,
    MAFIA_WAKE,
    MAFIA_CLOSE,
    DOCTOR_WAKE,
    DOCTOR_CLOSE,
    DETECTIVE_WAKE,
    DETECTIVE_CLOSE,
    MAFIA_WIN,
    VILLAGER_WIN,
    START_VOTE,
    VOTE_KICKED,
    VOTE_NOTKICKED
}
@Serializable
data class AudioState(val audioToPlay: AudioToPlay?)