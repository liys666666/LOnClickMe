# LOnClickMe

#### 介绍
本框架是基于ViewBinding上使用的, 通过apt自动生成setOnClick代码


#### 使用说明

**1.  项目根目录下 build.gradle**
```
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' } //添加
        }
    }
```

**2. app目录下 build.gradle**
```
dependencies {
    implementation 'com.gitee.liys666666:LOnClickMe:1.1.20'
    annotationProcessor 'com.gitee.liys666666.LOnClickMe:onclickme-compiler:1.1.20'
}
```
**3. Activity 或 Fragment中**
```
    //参数1：AClick注解所在对象
    //参数2：ViewBinding对象
    LOnClickMe.bind(this, binding); //初始化
```
```
    @AClick(ids = {"btn_activity", "btn_activity2"}, binding = xxxBinding.class)
    public void click(View view, String idType) {
        switch (idType) {
            case "btn_activity":
                Toast.makeText(this, "btn_activity", Toast.LENGTH_SHORT).show();
                break;
            case "btn_activity2":
                Toast.makeText(this, "btn_activity2", Toast.LENGTH_SHORT).show();
                break;
        }
    }
```
**说明：**
1. @AClick中的值, 使用字符串, 是为了避免在module中, R.id.xxx不是常量出现的一些问题.
2. 以上代码可通过插件[OnClickMe](https://plugins.jetbrains.com/plugin/14634-onclickme)自动生成, 用法类似Butterknife，可以在Android studio上搜索安装
3. 插件版本为2.3.0，如果AS上搜索不到，[点击此下载](https://gitee.com/liys666666/LOnClickMe/blob/master/OnClickMe2.3.0.jar)

![OnClickMe插件预览.gif](https://gitee.com/liys666666/LOnClickMe/blob/master/OnClickMe.gif)
