package com.liys.onclickme;

import android.util.Log;

import androidx.viewbinding.ViewBinding;


/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/6 17:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/6 17:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LOnClickMe {

    public static void bind(Object target, ViewBinding binding){
        String className = target.getClass().getCanonicalName()+"_OnClickMe";
        try {
            Class clazz =  Class.forName(className);
            OnClickMeStandard standard = (OnClickMeStandard)clazz.newInstance();
            standard.bind(target, binding);
            Log.d("66", "初始化完成...");
        } catch (IllegalAccessException ignored) {
            Log.d("66", "ignored");
        } catch (InstantiationException e) {
            Log.d("66", "InstantiationException");
        } catch (ClassNotFoundException e) {
            Log.d("66", "ClassNotFoundException");
        }
    }
}
