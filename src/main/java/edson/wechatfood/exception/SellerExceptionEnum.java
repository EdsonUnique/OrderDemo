package edson.wechatfood.exception;

import lombok.Getter;

public enum SellerExceptionEnum {

    SELLER_NOT_LOGIN(1,"用户未登录");

    @Getter
    private Integer code;
    @Getter
    private String message;

    SellerExceptionEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }

}
