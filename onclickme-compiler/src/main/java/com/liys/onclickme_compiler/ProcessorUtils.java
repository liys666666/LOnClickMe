package com.liys.onclickme_compiler;

import com.squareup.javapoet.ClassName;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/14 10:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/14 10:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProcessorUtils {

    public static ClassName viewClass = ClassName.get("android.view", "View");
    public static ClassName onClickListenerClass = ClassName.get("android.view.View", "OnClickListener");
    public static String userName = "user";

    /**
     * 获取R
     * @param R_Package R所在包名
     * @return
     */
    public static ClassName getRClass(String R_Package){
        return ClassName.get(R_Package, "R");
    }
}
