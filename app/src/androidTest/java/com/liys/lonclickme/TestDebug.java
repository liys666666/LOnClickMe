package com.liys.lonclickme;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2021/12/21 11:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/21 11:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestDebug {

    @Test
    public void main(){
        String str = idName2binding("_btn_act");
        System.out.println("str="+str);
    }


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
