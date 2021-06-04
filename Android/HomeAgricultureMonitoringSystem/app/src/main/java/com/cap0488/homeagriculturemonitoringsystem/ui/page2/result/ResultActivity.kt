package com.cap0488.homeagriculturemonitoringsystem.ui.page2.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding:ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object{
        const val EXTRA = "uri"
        const val HASIL = "hasil"
    }

}