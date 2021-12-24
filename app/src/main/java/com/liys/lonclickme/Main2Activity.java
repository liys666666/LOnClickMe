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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MyFragment());
        LOnClickMe.bind(this, binding); //添加
    }

    @AClick(ids = {"btn_activity", "btn_activity2"}, binding = ActivityMainBinding.class)
    public void click(View view, String idType) {
        switch (idType) {
            case "btn_activity":
                Toast.makeText(this, "btn_activity", Toast.LENGTH_SHORT).show();
                break;
            case "btn_activity2":
                Toast.makeText(this, "btn_activity2", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_fragment, fragment);
        transaction.commit();
    }
}
