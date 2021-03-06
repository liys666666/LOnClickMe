package com.liys.onclickme_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Description: viewIdName 集合
 * @Author: liys
 * @CreateDate: 2021/12/7 10:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/7 10:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AClick {
    String[] ids();
    Class<?> binding();
}
