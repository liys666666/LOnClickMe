package com.liys.onclickme_compiler;

import com.google.auto.service.AutoService;
import com.liys.onclickme_annotations.LOnClick;
import com.liys.onclickme_annotations.OnClickMeStandard;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.swing.text.View;
import javax.tools.Diagnostic;

public
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
@SupportedAnnotationTypes({"com.liys.onclickme_annotations.LOnClick"}) // 注解
@SupportedSourceVersion(SourceVersion.RELEASE_7) //环境版本

class OnClickMeProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        print(">>>>>>>>>>>>>监听成功， 编译中....");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LOnClick.class);
        if(ProcessorUtils.isEmpty(elements)){
            return false;
        }


        for (Element element : elements) {
            LOnClick onClick = element.getAnnotation(LOnClick.class);
//            print("\n\n注解对应的值=" + onClick.value()[0]);
//            print("方法名称=" + element.getSimpleName());
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
//            print("类名=" + typeElement.getSimpleName());
//            print("包名+类名=" + typeElement.getQualifiedName());
//            // 获取包名
            String packageName = this.elements.getPackageOf(typeElement).getQualifiedName().toString();
//            print("包名=" + packageName);


            try {
                //1.方法
                String packageClassName = typeElement.getQualifiedName().toString(); //包名+类名
                String methodName = element.getSimpleName().toString(); //方法名
                MethodSpec.Builder methodSpecBuilder = MethodSpec
                        .methodBuilder("init")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC) //修饰符
                        .returns(TypeName.VOID) //返回值
                        .addParameter(Object.class, "target")
                        .addParameter(Object.class, "sourceView")
//                        .addParameter(ClassName.get("android.view", "View"), "source")
//                        .addStatement("$T.out.print($S)", System.class, ".....编译期, 生成的类")

                        .addStatement("if(!(target instanceof " + packageClassName + ")){\nreturn", "")
                        .addCode("}\n", "")

                        .addStatement("final " + packageClassName + " mainActivity = (" + packageClassName + ")target", "")

                        .addStatement("android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() {"
                                        + "\n@Override"
                                        + "\npublic void onClick(android.view.View v) {"
                                        + "\n\tmainActivity." + methodName + "(v);"
                                        + "\n}"
                                , "")
                        .addCode("};\n");

                for (int i = 0; i < onClick.value().length; i++) {
                    methodSpecBuilder.addStatement("((android.view.View)sourceView).findViewById(" + onClick.value()[i] + ").setOnClickListener(onClickListener)", "");
                }


                //2.类名
                TypeSpec typeSpec = TypeSpec
                        .classBuilder(typeElement.getSimpleName() + "_OnClickMe")
                        .addSuperinterface(ClassName.get(OnClickMeStandard.class))
                        .addMethod(methodSpecBuilder.build())
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL) //修饰符
                        .build();

                //3.包名
                JavaFile packages = JavaFile.builder(packageName, typeSpec).build();
                packages.writeTo(filer);
//                print("生成文件....成功");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }





    /**
     * 打印信息
     * @param msg
     */
    void print(String msg){
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

}
