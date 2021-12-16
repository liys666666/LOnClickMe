package com.liys.lonclickme

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.liys.onclickme.LOnClickMe
import com.liys.onclickme_annotations.AClick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LOnClickMe.init(this)
    }


    @AClick(*[R.id.btn_activity])
    fun onClick(view: View){
        Log.d("66", "点击MainActivity...")
        when(view.id){
            R.id.btn_activity->{
            }
            R.id.btn_activity2->{
            }
        }
    }

}