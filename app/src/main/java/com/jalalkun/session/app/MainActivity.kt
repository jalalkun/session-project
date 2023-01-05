package com.jalalkun.session.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jalalkun.session.Session
import com.jalalkun.session.app.ui.theme.SessionAppComposeTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SessionAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
                Session.clear()
                Session.remove("")
                Session.saveFloat("float", 5f)
                Session.saveBoolean("boolean", true)
                Session.saveInt("int", 100)
                Session.saveLong("long", 120L)
                Session.saveString("string", "this is string")
                Session.saveModel("person", Person("Jalal", 25))
                Log.e(TAG, "onCreate: float ${Session.getFloat("float")}")
                Log.e(TAG, "onCreate: boolean ${Session.getBoolean("boolean")}")
                Log.e(TAG, "onCreate: Int ${Session.getInt("int")}")
                Log.e(TAG, "onCreate: Long ${Session.getLong("long")}")
                Log.e(TAG, "onCreate: String ${Session.getString("string")}")
                Log.e(TAG, "onCreate: person ${Session.getModel<Person>("person")}")
            }
        }
    }
}

data class Person(
    val name: String,
    val age: Int
)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
