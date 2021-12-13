package com.example.datastore

import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val TAG = this::class.java.simpleName
    private lateinit var preferencesDataStore: PreferenceManager
    private lateinit var protoDataStore: ProtoDataStoreManager
    private var settings = Settings(0, 0, 0)

    fun getCardCornerRadius() = preferencesDataStore.cardCornerRadiusFlow
    fun getCardBackgroundColor() = protoDataStore.settings.map { proto ->
        cardColorValues[proto.cardBackgroundColor].also {
            Log.d(TAG, "card color index: ${proto.cardBackgroundColor}")
        }
    }

    fun getBackgroundColor() = protoDataStore.settings.map { proto ->
        backgroundColorValues[proto.backgroundColor].also {
            Log.d(TAG, "background color index : ${proto.backgroundColor}")
        }
    }

    fun getTextColor() = protoDataStore.settings.map { proto ->
        textColorValues[proto.textColor].also {
            Log.d(TAG, "text color index: ${proto.textColor}")
        }
    }

    fun setDataStore(context: Context) {
        preferencesDataStore = PreferenceManager(context)
        protoDataStore = ProtoDataStoreManager(context)
    }

    fun updateCornerRadius(radius: Dp) {
        viewModelScope.launch {
            preferencesDataStore.updateSettings(radius)
        }
    }

    fun updateCardBackground(cardBgColor: Int) {
        settings = settings.copy(cardBackgroundColor = cardBgColor).also(::updateSettings)
    }

    fun updateBackgroundColor(bgColor: Int) {
        settings = settings.copy(backgroundColor = bgColor).also(::updateSettings)
    }

    fun updateTextColor(textColor: Int) {
        settings = settings.copy(textColor = textColor).also(::updateSettings)
    }

    private fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            protoDataStore.updateSettings(
                SettingsProto.newBuilder()
                    .setCardBackgroundColor(settings.cardBackgroundColor)
                    .setBackgroundColor(settings.backgroundColor)
                    .setTextColor(settings.textColor)
                    .build()
            )
        }
    }

    data class Settings(
        val cardBackgroundColor: Int,
        val backgroundColor: Int,
        val textColor: Int
    )
}