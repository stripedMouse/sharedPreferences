package com.example.sharedpreferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferences.databinding.ActivityMainBinding

const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_SOME_TEXT_VALUE = "PREF_SOME_TEXT_VALUE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if(key == PREF_SOME_TEXT_VALUE) {
            binding.tvShowInfo.text = preferences.getString(key, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val currentValue = preferences.getString(PREF_SOME_TEXT_VALUE, "")
        with(binding) {
            edText.setText(currentValue)
            tvShowInfo.text = currentValue
        }


        binding.bSave.setOnClickListener {
            val value = binding.edText.text.toString()
            preferences.edit()
                .putString(PREF_SOME_TEXT_VALUE, value)
                .apply()
        }
        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
    }

}