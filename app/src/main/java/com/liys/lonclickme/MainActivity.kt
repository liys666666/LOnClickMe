package com.liys.lonclickme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.liys.onclickme_annotations.LOnClick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LOnClickMe.init(this)
    }


    @LOnClick(*[R.id.tv_text, R.id.tv_text])
    fun onClick(view: View){
        Log.d("66", "1111")
    }

}