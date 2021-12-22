package com.liys.onclickme_compiler.utils;

import com.liys.onclickme_annotations.AClick;
import com.liys.onclickme_compiler.bean.AClassInfo;
import com.liys.onclickme_compiler.utils.EmptyUtils;
import com.squareup.javapoet.ClassName;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

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
    public static ClassName bindingClass = ClassName.get("androidx.viewbinding", "ViewBinding");
    public static ClassName onClickListenerClass = ClassName.get("android.view.View", "OnClickListener");
    public static ClassName onClickMeStandardClass = ClassName.get("com.liys.onclickme", "OnClickMeStandard");


    public static String userName = "user";
    public static String bindingName = "binding";

    /**
     * 获取R
     * @param R_Package R所在包名
     * @return
     */
    public static ClassName getRClass(String R_Package){
        return ClassName.get(R_Package, "R");
    }

    /**
     * 注解信息 --注入--> map集合
     * @param elements
     * @param element
     * @param classInfoMap
     */
    public static void inject(Elements elements, Element element, Map<String, AClassInfo> classInfoMap){
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString(); //包名
        String className = typeElement.getSimpleName().toString();  //类名
        String methodName = element.getSimpleName().toString(); //方法名
        String packageClassName = typeElement.getQualifiedName().toString(); //全类名=包名+类名
        //注解信息
        AClick aClick = element.getAnnotation(AClick.class);
        ClassName bindingClassName = null;
        String bindingInfo = "";

        List<? extends AnnotationMirror> list =  element.getAnnotationMirrors();
        for (int i = 0; i < list.size(); i++) {
            Map<? extends ExecutableElement, ? extends AnnotationValue> map = list.get(i).getElementValues();
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : map.entrySet()) {
                if("binding()".equals(entry.getKey().toString())){
                    bindingInfo = entry.getValue().toString().replace(".class", ""); //com.liys.lonclickme.databinding.ActivityMainBinding
                    int lastIndex = bindingInfo.lastIndexOf(".");
                    bindingClassName = ClassName.get(bindingInfo.substring(0, lastIndex), bindingInfo.substring(lastIndex+1));
                }
            }
        }

        if(bindingClassName==null){ //对应的binding不存在
            throw new NoClassDefFoundError("找不到binding类："+ bindingInfo);
        }

        //把注解信息添加集合
        AClassInfo aClassInfo = classInfoMap.get(packageClassName);
        if(aClassInfo==null){ //添加
            aClassInfo = new AClassInfo();
            aClassInfo.setThisClass(ClassName.get(packageName, className))
                    .setBindingClass(bindingClassName);
            aClassInfo.putIds(aClick.ids(), methodName);
            classInfoMap.put(packageClassName, aClassInfo);
        }else{ //已存在
            aClassInfo.putIds(aClick.ids(), methodName);
        }
    }


//    /**
//     * 获取Binding的类对应的ClassName  例如： ClassName.get("com.liys.lonclickme.databinding", "ActivityMainBinding");
//     * @param bindingElement
//     * @return
//     */
//    public static ClassName getBinding(Set<? extends Element> bindingElement){
//        if(EmptyUtils.isEmpty(bindingElement)){
//            return null;
//        }
//        for (Element element : bindingElement) {
//            List<? extends AnnotationMirror> list =  element.getAnnotationMirrors();
//            for (int i = 0; i < list.size(); i++) {
//                Map<? extends ExecutableElement, ? extends AnnotationValue> map = list.get(i).getElementValues();
//                for(AnnotationValue value : map.values()){
//                    //com.liys.lonclickme.databinding.ActivityMainBinding
//                    String packageClassName = value.toString().replace(".class", "");
//                    int lastIndex = packageClassName.lastIndexOf(".");
//                    return ClassName.get(packageClassName.substring(0, lastIndex), packageClassName.substring(lastIndex+1));
//                }
//            }
//        }
//        return null;
//    }
}
