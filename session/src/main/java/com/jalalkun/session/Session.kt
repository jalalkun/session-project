package com.jalalkun.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Session {
    private lateinit var preferences: SharedPreferences
    private fun masterKey(context: Context): MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    fun init(context: Context, fileName: String) {
        preferences = EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveString(tag: String, value: String) {
        checkInit()
        with(preferences.edit()) {
            putString(tag, value)
            apply()
        }
    }

    fun saveBoolean(tag: String, value: Boolean) {
        checkInit()
        with(preferences.edit()) {
            putBoolean(tag, value)
            apply()
        }
    }

    fun saveInt(tag: String, value: Int) {
        checkInit()
        with(preferences.edit()) {
            putInt(tag, value)
            apply()
        }
    }

    fun saveFloat(tag: String, value: Float) {
        checkInit()
        with(preferences.edit()) {
            putFloat(tag, value)
            apply()
        }
    }

    fun <T> saveModel(tag: String, value: T) {
        with(preferences.edit()) {
            putString(tag, Gson().toJson(value))
            apply()
        }
    }

    fun saveLong(tag: String, value: Long) {
        checkInit()
        with(preferences.edit()) {
            putLong(tag, value)
            apply()
        }
    }

    fun getBoolean(tag: String, default: Boolean = false): Boolean {
        checkInit()
        return getBoolean(tag, default)
    }

    fun getString(tag: String): String {
        checkInit()
        return preferences.getString(tag, "") ?: ""
    }

    fun getInt(tag: String): Int {
        checkInit()
        return preferences.getInt(tag, 0)
    }

    fun getFloat(tag: String): Float {
        checkInit()
        return preferences.getFloat(tag, 0f)
    }

    fun getLong(tag: String): Long {
        checkInit()
        return preferences.getLong(tag, 0L)
    }

    fun <T> getModel(tag: String): T {
        checkInit()
        val json: String = preferences.getString(tag, "") ?: ""
        if (json.isEmpty()) throw IllegalArgumentException("Please check your tag, the value is empty")
        val type = object : TypeToken<T?>() {}.type
        return Gson().fromJson(json, type)
    }

    fun remove(tag: String) {
        checkInit()
        with(preferences.edit()) {
            remove(tag)
            apply()
        }
    }

    fun clear() {
        checkInit()
        with(preferences.edit()) {
            clear()
            apply()
        }
    }

    private fun checkInit() {
        if (!this::preferences.isInitialized) {
            throw IllegalArgumentException("Please init this Session in your Application")
        }
    }
}
