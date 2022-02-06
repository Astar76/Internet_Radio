package com.astar.myapplication.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.astar.myapplication.R
import com.astar.myapplication.radioplayer.RadioService
import com.astar.myapplication.presentation.radiolist.RadioListFragment
import com.astar.myapplication.servicelocator.RadioApp

class MainActivity : AppCompatActivity() {

    fun <T : ViewModel> viewModel(model: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as RadioApp).viewModel(model, owner)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, RadioListFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()

        startService(Intent(this, RadioService::class.java))
    }
}