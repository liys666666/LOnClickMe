package com.liys.onclickme_compiler;

import com.google.auto.service.AutoService;
import com.liys.onclickme_annotations.LOnClick;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
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
//        print(">>>>>>>>>>>>>OnClickMe初始化中....");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        print(">>>>>>>>>>>>>监听成功， 编译中....");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LOnClick.class);
        if(ProcessorUtils.isEmpty(elements)){
            return false;
        }


        for (Element element : elements) {
            //TODO 1. 获取参数
            LOnClick onClick = element.getAnnotation(LOnClick.class); //注解
            String methodName = element.getSimpleName().toString(); //方法名
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            //包名
            String packageName = this.elements.getPackageOf(typeElement).getQualifiedName().toString();
            //类名
            String className = typeElement.getSimpleName().toString();
            //全类名=包名+类名
//            String packageClassName = typeElement.getQualifiedName().toString();

            //TODO 2. 成功对应类
            try {
                createOnClickMe(packageName, className, methodName, onClick);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 创建ActivityOnClickMe
     * @param packageName 包名
     * @param className 类名
     * @param methodName 方法名
     * @param onClick 注解
     * @throws IOException
     */
    private void createOnClickMe(String packageName, String className, String methodName, LOnClick onClick) throws IOException {
        //1.方法
        String packageClassName = packageName + "."+className; //包名+类名
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
                .classBuilder(className + "_OnClickMe")
                .addSuperinterface(ClassName.get("com.liys.onclickme", "OnClickMeStandard"))
//                        .addSuperinterface(ClassName.get(OnClickMeStandard.class))
                .addMethod(methodSpecBuilder.build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL) //修饰符
                .build();

        //3.包名
        JavaFile packages = JavaFile.builder(packageName, typeSpec).build();
        packages.writeTo(filer);
        print("OnClickMe生成文件....成功");
    }


    /**
     * 打印信息
     * @param msg
     */
    void print(String msg){
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

}
