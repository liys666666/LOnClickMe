package com.liys.onclickme_compiler;

import com.liys.onclickme_compiler.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedOptions;
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
    protected Elements elements;
    protected Messager messager;
    protected String R_Package = ""; // app/module包名
    protected ClassName RClass = null;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();

        //拿到R类
//        RClass = ProcessorUtils.getRClass(R_Package);
        R_Package = processingEnvironment.getOptions().get(ProcessorConfig.R_Package);
        if(R_Package==null){
            R_Package = "";
        }
        RClass = ProcessorUtils.getRClass(R_Package);
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
        messager.printMessage(Diagnostic.Kind.NOTE, msg+"\n");
    }
}
