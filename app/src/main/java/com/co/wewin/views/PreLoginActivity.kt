package com.co.wewin.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.co.wewin.R
import com.co.wewin.databinding.ActivityPostLoginBinding
import com.co.wewin.databinding.ActivityPreLoginBinding

class PreLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPreLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}