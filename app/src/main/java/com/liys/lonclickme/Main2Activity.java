package com.liys.lonclickme;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.liys.onclickme_annotations.LOnClick;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/7 16:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/7 16:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LOnClickMe.init(this);
    }

    @LOnClick({R.id.tv_text})
    void click(View v){
        Log.d("66", "222");
    }
}
