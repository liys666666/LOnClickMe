package com.liys.lonclickme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.liys.onclickme.LOnClickMe;
import com.liys.onclickme_annotations.LOnClick;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/13 11:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/13 11:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        LOnClickMe.init(this, root);
        return root;
    }


    @LOnClick({R.id.btn_fragment})
    void click(View v){
        Log.d("66", "点击Fragment...");
        Toast.makeText(getContext(), "点击Fragment...", Toast.LENGTH_SHORT).show();
    }
}
