package com.liys.onclickme_compiler.bean;

import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 注解--->类信息
 * @Author: liys
 * @CreateDate: 2021/12/20 17:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/20 17:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AClassInfo {

    ClassName thisClass;  //注解所在类
    ClassName bindingClass; //binding类
    //<id名, 方法名>
    private Map<String, String> idMap = new HashMap<>();

    public ClassName getThisClass() {
        return thisClass;
    }

    public AClassInfo setThisClass(ClassName thisClass) {
        this.thisClass = thisClass;
        return this;
    }

    public ClassName getBindingClass() {
        return bindingClass;
    }

    public AClassInfo setBindingClass(ClassName bindingClass) {
        this.bindingClass = bindingClass;
        return this;
    }

    public Map<String, String> getIdMap() {
        return idMap;
    }

    /**
     * 添加id
     * @param ids id集合
     * @param methodName 对应的方法名
     * @return
     */
    public AClassInfo putIds(String[] ids, String methodName) {
        for (int i = 0; i < ids.length; i++) {
            idMap.put(ids[i], methodName);
        }
        return this;
    }

    @Override
    public String toString() {
        return "AClassInfo{" +
                "thisClass=" + thisClass +
                ", bindingClass=" + bindingClass +
                ", idMap=" + idMap +
                '}';
    }
}
