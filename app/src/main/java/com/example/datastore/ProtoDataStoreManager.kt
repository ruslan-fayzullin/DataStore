package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.collect

val Context.createDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.proto",
    serializer = SettingsSerializer
)

class ProtoDataStoreManager(context: Context) {

    private val dataStore = context.createDataStore

    val settings = dataStore.data

    suspend fun updateSettings(settingsProto: SettingsProto) {
        dataStore.updateData {
            it.toBuilder()
                .setCardBackgroundColor(settingsProto.cardBackgroundColor)
                .setBackgroundColor(settingsProto.backgroundColor)
                .setTextColor(settingsProto.textColor)
                .build()
        }
    }

}