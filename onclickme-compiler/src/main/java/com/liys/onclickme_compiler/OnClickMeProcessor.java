package com.liys.onclickme_compiler;

import com.google.auto.service.AutoService;
import com.liys.onclickme_annotations.AClick;
import com.liys.onclickme_compiler.bean.AClassInfo;
import com.liys.onclickme_compiler.utils.EmptyUtils;
import com.liys.onclickme_compiler.utils.ProcessorUtils;
import com.liys.onclickme_compiler.utils.StringUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


/**
 * @Description: 监听全局编译
 * @Author: liys
 * @CreateDate: 2020/11/16 10:14
 * @UpdateUser:
 * @UpdateDate: 2020/11/16 10:14
 * @UpdateRemark:
 * @Version: 1.0
 */
//@SupportedOptions("student")  //接收，安卓传递过来的参数
@AutoService(Processor.class) // 启用服务
@SupportedAnnotationTypes({
        "com.liys.onclickme_annotations.AClick",
//        "com.liys.onclickme_annotations.AClickBinding"
}) // 注解
@SupportedSourceVersion(SourceVersion.RELEASE_7) //环境版本
public class OnClickMeProcessor extends BaseProcessor {

    // <binding全类名, AClick注解信息>
    public static Map<String, AClassInfo> classInfoMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        return super.process(annotations, roundEnv);
//        Set<? extends Element> bindingElements = roundEnv.getElementsAnnotatedWith(AClickBinding.class);
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(AClick.class);
        if(EmptyUtils.isEmpty(elementSet)){
            return true;
        }

        for (Element element : elementSet) {
            ProcessorUtils.inject(elements, element, classInfoMap);
        }
//        print("classInfoMap="+classInfoMap.toString());
        for (AClassInfo aClassInfo: classInfoMap.values()) {
            ClassName binding = aClassInfo.getBindingClass();
            ClassName xxxOnClickMeClass = ClassName.get(aClassInfo.getThisClass().packageName(), aClassInfo.getThisClass().simpleName()+"_OnClickMe");
            createOnClickMe(binding, aClassInfo.getThisClass(), xxxOnClickMeClass, aClassInfo.getIdMap());
        }
        return true;
    }




    /**
     * 创建XXXActivity_OnClickMe
     * @param bindingClassName 对应binding信息
     * @param thisClass //调用者信息
     * @param xxxOnClickMeClass //最终类信息
     * @param idMap //方法注解信息
     */
    void createOnClickMe(ClassName bindingClassName, ClassName thisClass, ClassName xxxOnClickMeClass, Map<String, String> idMap){
        //1.方法
        MethodSpec.Builder methodSpecBuilder = MethodSpec
                .methodBuilder("bind")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC) //修饰符
                .returns(TypeName.VOID) //返回值
                .addParameter(Object.class, "target")
                .addParameter(ProcessorUtils.bindingClass, "viewBinding")
//                        .addStatement("$T.out.print($S)", System.class, ".....编译期, 生成的类")
                .addStatement("if(!(target instanceof $T)){\nreturn", thisClass)
                .addCode("}\n")
                .addStatement("if(!(viewBinding instanceof $T)){\nreturn", bindingClassName)
                .addCode("}\n")
                .addStatement("final $T "+ ProcessorUtils.userName +" = ($T)target", thisClass, thisClass)
                .addStatement("final $T "+ ProcessorUtils.bindingName +" = ($T)viewBinding", bindingClassName, bindingClassName);

        for (Map.Entry<String, String> entry : idMap.entrySet()) {
            String key = entry.getKey();
            String bindingIdName = StringUtils.idName2binding(key);
            methodSpecBuilder.addStatement(ProcessorUtils.bindingName + "."+ bindingIdName +".setOnClickListener("+
                    "new $T() {"
                    + "\n@Override"
                    + "\npublic void onClick($T v) {"
                    + "\n\t" + ProcessorUtils.userName + "." + entry.getValue() + "(v, \"" + key + "\");"
                    + "\n}", ProcessorUtils.onClickListenerClass, ProcessorUtils.viewClass);
            methodSpecBuilder.addStatement("})");

        }

        //2.类名
        TypeSpec typeSpec = TypeSpec
                .classBuilder(xxxOnClickMeClass.simpleName())
                .addSuperinterface(ProcessorUtils.onClickMeStandardClass)
//                        .addSuperinterface(ClassName.get(OnClickMeStandard.class))
                .addMethod(methodSpecBuilder.build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL) //修饰符
                .build();

        //3.包名
        createFile(xxxOnClickMeClass.packageName(), typeSpec);
    }
}
