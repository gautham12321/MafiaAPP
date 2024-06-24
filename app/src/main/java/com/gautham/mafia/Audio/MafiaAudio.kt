package com.gautham.mafia.Audio

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.gautham.mafia.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SoundCue(val sound:Int){

    VILLAGERCLOSE(R.raw.close),
    WAKE_NODEATH(R.raw.wake_nodeath),
    WAKE_WITHDEATH(R.raw.wake_death),
    MAFIA_WAKE(R.raw.mafia_open),
    MAFIA_CLOSE(R.raw.mafia_close),
    DOCTOR_WAKE(R.raw.doctor_open),
    DOCTOR_CLOSE(R.raw.doctor_close),
    DETECTIVE_WAKE(R.raw.detective_open),
    DETECTIVE_CLOSE(R.raw.detective_close),
    MAFIA_WIN(R.raw.mafia_win),
    VILLAGER_WIN(R.raw.villager_win),

}
class MafiaAudioViewModel:ViewModel() {

    val soundState = MutableStateFlow(true)
    val _soundState = soundState.asStateFlow()
    var mediaPlayer: MediaPlayer?=null
    fun playAudio(audio:SoundCue,context: Context){
        if(soundState.value) {

            mediaPlayer = MediaPlayer.create(context, audio.sound)
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.release()
            }
        }



    }

}

