package com.liys.lonclickme

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liys.lonclickme.databinding.ActivityMainBinding
import com.liys.onclickme.LOnClickMe
import com.liys.onclickme_annotations.AClick

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LOnClickMe.bind(this, binding)
    }


    @AClick(ids = ["btn_activity", "btn_activity2"], binding = ActivityMainBinding::class)
    fun click(view: View, idType: String) {
        when (idType) {
            "btn_activity" -> {
                Toast.makeText(this, "btn_activity", Toast.LENGTH_SHORT).show()
            }
            "btn_activity2" -> {
            }
        }
    }
}