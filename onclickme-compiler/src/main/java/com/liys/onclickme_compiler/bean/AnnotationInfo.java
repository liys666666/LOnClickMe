package com.liys.onclickme_compiler.bean;

import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 注解信息
 * @Author: liys
 * @CreateDate: 2021/12/14 14:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/14 14:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AnnotationInfo {

    ClassName thisClass;  //注解所在类
    ClassName bindingClass; //binding类
    //<id名, 方法名>
    private Map<String, String> idMap = new HashMap<>();


    public ClassName getThisClass() {
        return thisClass;
    }

    public AnnotationInfo setThisClass(ClassName thisClass) {
        this.thisClass = thisClass;
        return this;
    }

    public ClassName getBindingClass() {
        return bindingClass;
    }

    public AnnotationInfo setBindingClass(ClassName bindingClass) {
        this.bindingClass = bindingClass;
        return this;
    }

    public Map<String, String> getIdMap() {
        return idMap;
    }

    public AnnotationInfo setIdMap(Map<String, String> idMap) {
        this.idMap = idMap;
        return this;
    }
}
