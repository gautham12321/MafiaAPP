package com.gautham.mafia.Datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.Extras.SettingClass
import com.gautham.mafia.R
import com.gautham.mafia.dataStore
import com.mafia2.data.PlayerDet
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


class DataStoreRepository(val context: Context){
val TAG ="USERDATASTORE"

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val IMAGE_ID = intPreferencesKey("image_id")
        val RANDOMAVATAR = booleanPreferencesKey("random_avatar")



    }

    val userPrefsFlow = context.dataStore.data.catch {
        exception ->
        if(exception is IOException){
            Log.e(TAG, "Error reading preferences.", exception)
            emit(emptyPreferences())


        }else{ Log.e(TAG, "Error FATAL.", exception)
            throw exception
        }


    }.map { preferences ->

        mapToPlayerDetails(preferences)
    }
    val settingsFlow = context.dataStore.data.catch {
            exception ->
        if(exception is IOException){
            Log.e(TAG, "Error reading preferences.", exception)
            emit(emptyPreferences())


        }else{
            throw exception
        }


    }.onEach {  Log.d("SETTINGS","7.$it") }.map { preferences ->

        mapToSettings(preferences)
    }
    suspend fun UpdateUserDetails(playerDet: PlayerDet){

        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = playerDet.name
            preferences[PreferencesKeys.IMAGE_ID] = playerDet.avatar.imageId
        }
    }
    suspend fun UpdateSettings(settings:List<SettingClass>)
    {

        settings.forEach {
            when(it.label){
                context.getString(R.string.randomAvatar) ->{
                    context.dataStore.edit { preferences ->
                        preferences[PreferencesKeys.RANDOMAVATAR] = it.state

                    }
                }






            }

        }
        Log.d("SETTINGS","1.${settingsFlow.toString()}")



    }
    suspend fun fetchIntialPreferences()=mapToPlayerDetails(context.dataStore.data.first())
    fun mapToPlayerDetails(preferences: Preferences):PlayerDet {
        val userName = preferences[PreferencesKeys.USER_NAME] ?: "LIGMA"
        val imageId = preferences[PreferencesKeys.IMAGE_ID] ?: R.drawable._043232_avatar_batman_comics_hero_icon
        return PlayerDet(userName, Avatar(imageId))


    }
    fun mapToSettings(preferences: Preferences):List<SettingClass>{
        val ramdomEnabled = preferences[PreferencesKeys.RANDOMAVATAR] ?: false

        return listOf(SettingClass(context.getString(R.string.randomAvatar), ramdomEnabled))


    }


}