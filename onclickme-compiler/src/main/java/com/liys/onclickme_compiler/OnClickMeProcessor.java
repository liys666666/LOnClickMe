package com.liys.onclickme_compiler;

import com.google.auto.service.AutoService;
import com.liys.onclickme_annotations.AClick;
import com.liys.onclickme_compiler.bean.AnnotationInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;


/**
 * @Description: 监听全局编译
 * @Author: liys
 * @CreateDate: 2020/11/16 10:14
 * @UpdateUser:
 * @UpdateDate: 2020/11/16 10:14
 * @UpdateRemark:
 * @Version: 1.0
 */
@AutoService(Processor.class) // 启用服务
@SupportedAnnotationTypes({"com.liys.onclickme_annotations.AClick"}) // 注解
@SupportedSourceVersion(SourceVersion.RELEASE_7) //环境版本
public class OnClickMeProcessor extends BaseProcessor {

    //注解信息集合<全类名, 注解信息>
    HashMap<String, AnnotationInfo<Integer>> annotationInfoMap = new HashMap<>();

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return AClick.class;
    }

    @Override
    public void process(Element element, String packageName, String className, String packageClassName) {
        int[] ids = element.getAnnotation(AClick.class).value();
        String methodName = element.getSimpleName().toString(); //方法名

        if(annotationInfoMap.containsKey(packageClassName)){ //已存在
            AnnotationInfo<Integer> annotationInfo = annotationInfoMap.get(packageClassName);
            for (int i = 0; i < ids.length; i++) {
                annotationInfo.put(ids[i], methodName);
            }
        }else{ //新的类
            AnnotationInfo<Integer> annotationInfo = new AnnotationInfo<>();
            annotationInfo.setClassName(className)
                    .setPackageName(packageName);

            for (int i = 0; i < ids.length; i++) {
                annotationInfo.put(ids[i], methodName);
            }
            annotationInfoMap.put(packageClassName, annotationInfo);
        }
    }

    @Override
    public void processEnd() {
        for (AnnotationInfo<Integer> annotationInfo : annotationInfoMap.values()) {
            createOnClickMe(annotationInfo.getPackageName(), annotationInfo.getClassName(), annotationInfo.getIdMap());
        }
    }

    /**
     * 创建XXXActivity_OnClickMe
     * @param packageName 包名
     * @param className 类名
     * @param idMap  <id, 方法名>  <123456, "click">
     */
    private void createOnClickMe(String packageName, String className,Map<Integer, String> idMap){
        if(idMap==null || idMap.size()==0){
            return;
        }
        //参数
        ClassName thisClass = ClassName.get(packageName, className);   //Main2Activity
        //1.方法
        MethodSpec.Builder methodSpecBuilder = MethodSpec
                .methodBuilder("init")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC) //修饰符
                .returns(TypeName.VOID) //返回值
                .addParameter(Object.class, "target")
                .addParameter(ProcessorUtils.viewClass, "sourceView")
//                        .addStatement("$T.out.print($S)", System.class, ".....编译期, 生成的类")
                .addStatement("if(!(target instanceof $T)){\nreturn", thisClass)
                .addCode("}\n")
                .addStatement("final $T " + ProcessorUtils.userName +" = ($T)target", thisClass, thisClass);

        for (Map.Entry<Integer, String> entry : idMap.entrySet()) {
            methodSpecBuilder.addStatement("sourceView.findViewById("+ entry.getKey() +").setOnClickListener("+
                    "new $T() {"
                    + "\n@Override"
                    + "\npublic void onClick($T v) {"
                    + "\n\t" + ProcessorUtils.userName + "." + entry.getValue() + "(v);"
                    + "\n}", ProcessorUtils.onClickListenerClass, ProcessorUtils.viewClass);
            methodSpecBuilder.addStatement("})");

        }

        //2.类名
        TypeSpec typeSpec = TypeSpec
                .classBuilder(className + "_OnClickMe")
                .addSuperinterface(ClassName.get("com.liys.onclickme", "OnClickMeStandard"))
//                        .addSuperinterface(ClassName.get(OnClickMeStandard.class))
                .addMethod(methodSpecBuilder.build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL) //修饰符
                .build();

        //3.包名
        createFile(packageName, typeSpec);
    }

}
