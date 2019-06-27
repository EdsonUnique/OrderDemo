package edson.wechatfood.enums;

import lombok.Getter;

/**
 * 订单状态
 */
public enum OrderStatus implements PageEnum{

    NEW_ORDER(0,"新订单"),
    OLD_ORDER(1,"订单已取消"),
    FINISH_ORDER(2,"交易完成");

    @Getter
    private Integer code;

    @Getter
    private String message;

    OrderStatus(Integer code,String message){
        this.code=code;
        this.message=message;
    }



}
