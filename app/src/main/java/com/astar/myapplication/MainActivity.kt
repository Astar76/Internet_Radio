package com.astar.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astar.myapplication.presentation.RadioControlFragment
import com.astar.myapplication.presentation.RadioListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, RadioListFragment())
                .commit()
        }
    }
}