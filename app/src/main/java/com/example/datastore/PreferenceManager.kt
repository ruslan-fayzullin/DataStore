package com.example.datastore

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.mapNotNull

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager(context: Context) {

    private val dataStore = context.dataStore

    suspend fun updateSettings(
        cornerRadius: Dp
    ) {
        dataStore.edit {
            it[SettingsScheme.FIELD_CARD_RADIUS] = cornerRadius.value
        }
    }

    val cardCornerRadiusFlow = dataStore.data.mapNotNull {
        it[SettingsScheme.FIELD_CARD_RADIUS]?.dp
    }

}

