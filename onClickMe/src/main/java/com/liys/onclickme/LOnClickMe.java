package com.liys.onclickme;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;


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

    public static void init(Activity target){
        init(target, target.getWindow().getDecorView());
    }

    public static void init(Object target, View source){
        String className = target.getClass().getCanonicalName()+"_OnClickMe";
        try {
            Class clazz =  Class.forName(className);
            OnClickMeStandard standard = (OnClickMeStandard)clazz.newInstance();
            standard.init(target, source);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //本类---无注解
        }
    }
}
