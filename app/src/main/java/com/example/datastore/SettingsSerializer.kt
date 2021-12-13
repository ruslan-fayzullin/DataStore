package com.example.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer: Serializer<SettingsProto> {
    override val defaultValue: SettingsProto = SettingsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsProto =
        try {
            SettingsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: SettingsProto, output: OutputStream) {
        t.writeTo(output)
    }
}