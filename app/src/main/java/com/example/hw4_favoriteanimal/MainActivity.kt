package com.example.hw4_favoriteanimal

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, Main_Fragment())
            .addToBackStack(null)
            .commit()
        // If we are in landscape orientation, Load the Detail fragment into the details_container
        // so that both fragments are shown side by side
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, Rating_Fragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
