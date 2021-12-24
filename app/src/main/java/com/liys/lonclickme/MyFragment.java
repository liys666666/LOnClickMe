package com.liys.lonclickme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liys.lonclickme.databinding.FragmentMyBinding;
import com.liys.onclickme.LOnClickMe;
import com.liys.onclickme_annotations.AClick;

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

    FragmentMyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("66", "初始化");
        LOnClickMe.bind(this, binding);
    }

    @AClick(ids = {"btn_fragment", "btn_fragment2"}, binding = FragmentMyBinding.class)
    public void click(View view, String idType) {
        Log.d("66", "idType="+idType);
        switch (idType) {
            case "btn_fragment":
                Toast.makeText(getActivity(), "btn_fragment", Toast.LENGTH_SHORT).show();
                break;
            case "btn_fragment2":
                Toast.makeText(getActivity(), "btn_fragment2", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
