package com.liys.lonclickme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liys.onclickme.LOnClickMe

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LOnClickMe.init(this)
    }


//    @LOnClick(*[R.id.btn_activity])
//    fun onClick(view: View){
//        Log.d("66", "点击MainActivity...")
//    }

}