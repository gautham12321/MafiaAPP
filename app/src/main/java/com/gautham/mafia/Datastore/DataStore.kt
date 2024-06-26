package com.gautham.mafia.Datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gautham.mafia.Data.Avatar
import com.gautham.mafia.R
import com.gautham.mafia.dataStore
import com.mafia2.data.PlayerDet
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStoreRepository(val context: Context){
val TAG ="USERDATASTORE"

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val IMAGE_ID = intPreferencesKey("image_id")



    }
    val userPrefsFlow = context.dataStore.data.catch {
        exception ->
        if(exception is IOException){
            Log.e(TAG, "Error reading preferences.", exception)
            emit(emptyPreferences())


        }else{
            throw exception
        }


    }.map { preferences ->

        mapToPlayerDetails(preferences)
    }
    suspend fun UpdateUserDetails(playerDet: PlayerDet){

        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = playerDet.name
            preferences[PreferencesKeys.IMAGE_ID] = playerDet.avatar.imageId
        }
    }
    suspend fun fetchIntialPreferences()=mapToPlayerDetails(context.dataStore.data.first())
    fun mapToPlayerDetails(preferences: Preferences):PlayerDet {
        val userName = preferences[PreferencesKeys.USER_NAME] ?: "LIGMA"
        val imageId = preferences[PreferencesKeys.IMAGE_ID] ?: R.drawable._043232_avatar_batman_comics_hero_icon
        return PlayerDet(userName, Avatar(imageId))


    }


}