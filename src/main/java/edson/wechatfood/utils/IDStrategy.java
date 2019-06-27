package edson.wechatfood.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * 主键生成策略
 */
public class IDStrategy {

    public static String IDString="";

    public static synchronized String generateID(){
        IDString=new String(System.currentTimeMillis()+""+ RandomStringUtils.random(10,"wtgfsnasbADCSodvvPLKMNA")).substring(0,20);
        return IDString;
    }

}
