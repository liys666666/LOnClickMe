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
import androidx.viewbinding.ViewBinding;

import com.liys.lonclickme.databinding.ActivityMainBinding;
import com.liys.onclickme.LOnClickMe;
import com.liys.onclickme.OnClickMeStandard;
import com.liys.onclickme_annotations.AClick;

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

    String TAG = "66";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LOnClickMe.bind(this, binding); //添加
    }
    
    private void setOnClick(){
        binding.btnActivity.setOnClickListener(v -> {

        });
        binding.btnActivity2.setOnClickListener(v -> {

        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_fragment, fragment);
        transaction.commit();
    }

    @AClick(ids = {"btn_activity", "btn_activity2", "fragment.btn_fragment", "fragment.btn_fragment2"}, binding = ActivityMainBinding.class)
    public void click(View view, String idType) {
//        switch (idType) {
//            case "btn_activity":
//                break;
//            case "btn_activity2":
//                break;
//            case "fragment.btn_fragment":
//                break;
//            case "fragment.btn_fragment2":
//                break;
//        }
    }


//    @AClick(ids={"btn_activity", "btn_activity2", "fragment.btn_fragment"}, binding=ActivityMainBinding.class)
//    public void click(View view, String idName) {
//        switch (idName) {
//            case "btn_activity":
//                Log.d(TAG, "点击activity...");
//                Toast.makeText(this, "点击activity...", Toast.LENGTH_SHORT).show();
//                break;
//            case "btn_activity2":
//                Log.d(TAG, "点击activity2...");
//                Toast.makeText(this, "点击activity2...", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}
