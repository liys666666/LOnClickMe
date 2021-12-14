package com.liys.onclickme_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/13 16:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/13 16:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
// 注解处理器接收的参数
@SupportedOptions({ProcessorConfig.R_Package})
public abstract class BaseProcessor extends AbstractProcessor{

    private Filer filer;
    private Elements elements;
    protected Messager messager;
    protected String R_Package = ""; // app/module包名
    protected ClassName RClass = null;

    /**
     * 获取注解class
     * @return
     */
    public abstract Class<? extends Annotation> getAnnotation();


    /**
     * 处理注解
     * @param element
     * @param packageName 包名
     * @param className 类名
     * @return
     */
    public abstract void process(Element element, String packageName, String className, String packageClassName);

    /**
     * end
     */
    public abstract void processEnd();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();

        //拿到R类
        R_Package = processingEnvironment.getOptions().get(ProcessorConfig.R_Package);
        if(R_Package!=null && !("".equals(R_Package.trim()))){
            RClass = ProcessorUtils.getRClass(R_Package);
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(getAnnotation());
        if(EmptyUtils.isEmpty(elements)){
            return false;
        }
        //解析
        for (Element element : elements) {
            //TODO 1. 获取参数
//            String methodName = element.getSimpleName().toString(); //方法名
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            //包名
            String packageName = this.elements.getPackageOf(typeElement).getQualifiedName().toString();
            //类名
            String className = typeElement.getSimpleName().toString();
            //全类名=包名+类名
            String packageClassName = typeElement.getQualifiedName().toString();

            process(element, packageName, className, packageClassName);
        }
        processEnd();
        return true;
    }

    /**
     * 创建文件
     * @param packageName
     * @param typeSpec
     */
    public void createFile(String packageName, TypeSpec typeSpec){
        JavaFile packages = JavaFile.builder(packageName, typeSpec).build();
        try {
            packages.writeTo(filer);
//            print("生成文件....成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印信息
     * @param msg
     */
    protected void print(String msg){
        messager.printMessage(Diagnostic.Kind.OTHER, msg);
    }
}
