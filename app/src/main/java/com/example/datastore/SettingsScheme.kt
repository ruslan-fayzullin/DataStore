package com.example.datastore

import androidx.datastore.preferences.core.floatPreferencesKey

object SettingsScheme {
    val FIELD_CARD_RADIUS = floatPreferencesKey("card_corner_radius")
}