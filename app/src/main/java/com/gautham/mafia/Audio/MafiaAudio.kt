package com.gautham.mafia.Audio

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.gautham.mafia.R
import com.gautham.mafia.R.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SoundCue(val sound:Int){

    VILLAGERCLOSE(raw.close),
    WAKE_NODEATH(raw.wake_nodeath),
    WAKE_WITHDEATH(raw.wake_death),
    MAFIA_WAKE(raw.mafia_open),
    MAFIA_CLOSE(raw.mafia_close),
    DOCTOR_WAKE(raw.doctor_open),
    DOCTOR_CLOSE(raw.doctor_close),
    DETECTIVE_WAKE(raw.detective_open),
    DETECTIVE_CLOSE(raw.detective_close),
    MAFIA_WIN(raw.mafia_win),
    VILLAGER_WIN(raw.villager_win),
    START_VOTE(raw.start_vote),
    VOTE_KICK(raw.vote_kicked),
    SKIP_VOTE(raw.vote_notkicked),
}


