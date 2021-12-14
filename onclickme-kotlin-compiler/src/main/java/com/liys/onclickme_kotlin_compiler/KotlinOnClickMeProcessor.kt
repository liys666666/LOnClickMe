package com.liys.onclickme_kotlin_compiler

import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.liys.onclickme_annotations.LOnClick") // 注解
@SupportedOptions("kapt.kotlin.generated")
class KotlinOnClickMeProcessor : AbstractProcessor() {

    private var messager : Messager? = null
    private var filer: Filer? = null;

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        messager = processingEnv.messager
        print("kotlin_poet 初始化中...")
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        print("kotlin_poet process中...")
        return true;
    }

    /**
     * 打印信息
     * @param msg
     */
    private fun print(msg: String?) {
        messager!!.printMessage(Diagnostic.Kind.NOTE, msg)
    }
}