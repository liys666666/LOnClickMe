package com.liys.lonclickme;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.liys.onclickme.LOnClickMe;
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
        replaceFragment(new MyFragment());
        LOnClickMe.init(this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_fragment, fragment);
        transaction.commit();
    }


    @LOnClick({R.id.btn_activity})
    void click(View v){
        Log.d("66", "点击activity...");
        Toast.makeText(this, "点击activity...", Toast.LENGTH_SHORT).show();
    }
}
