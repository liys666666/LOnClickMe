package com.liys.onclickme_compiler.utils;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/21 10:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/21 10:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StringUtils {

    /**
     * id名 ---> binding变量名   例如：btn_activity ---> btnActivity
     * @param idName
     * @return
     */
    public static String idName2binding(String idName){
        StringBuilder newIdName = new StringBuilder();
        String[] idPart = idName.split("_");
        if(idPart.length<2){ //无需处理
            return idName;
        }
        newIdName.append(idPart[0]);
        for (int i = 1; i < idPart.length; i++) {
            if(idPart[i].length()>0){
                String indexChar = idPart[i].charAt(0)+"";
                idPart[i] = idPart[i].replace(indexChar, indexChar.toUpperCase()); //首字母：小写--->大写
                newIdName.append(idPart[i]);
            }
        }
        return newIdName.toString();
    }
}
