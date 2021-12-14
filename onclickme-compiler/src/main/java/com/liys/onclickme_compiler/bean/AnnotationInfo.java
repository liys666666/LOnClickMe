package com.liys.onclickme_compiler.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/14 14:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/14 14:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AnnotationInfo<T> {

    private String packageName = ""; //包名
    private String className = "";//类名
    //<id, 方法名>
    private Map<T, String> idMap = new HashMap<>();

    public String getPackageName() {
        return packageName;
    }

    public AnnotationInfo setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public AnnotationInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public Map<T, String> getIdMap() {
        return idMap;
    }

    /**
     * 添加
     * @param key
     * @param methodName
     */
    public void put(T key, String methodName){
        idMap.put(key, methodName);
    }
}
