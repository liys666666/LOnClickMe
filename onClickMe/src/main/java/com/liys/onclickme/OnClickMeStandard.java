package com.liys.onclickme;


import android.view.View;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/6 17:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/6 17:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OnClickMeStandard {

    /**
     * 初始化
     * @param target 注解所在的类
     * @param sourceView view
     */
    void init(Object target, View sourceView);
}
