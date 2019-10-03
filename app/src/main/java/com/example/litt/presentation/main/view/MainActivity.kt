package com.example.litt.presentation.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.litt.R
import com.example.litt.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}
