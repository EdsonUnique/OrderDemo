package edson.wechatfood.utils;

import edson.wechatfood.enums.PageEnum;

/**
 * 获取某个枚举类
 */
public class GetPageEnum {

    public static <T extends PageEnum> T getPageEnum(Integer code,Class<T> enumClass){

        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }

}
