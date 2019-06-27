package edson.wechatfood.exception;

import lombok.Data;

@Data
public class SellerException extends RuntimeException {

    private Integer code;

    public SellerException(SellerExceptionEnum sellerExceptionEnum){
        super(sellerExceptionEnum.getMessage());
        this.code=sellerExceptionEnum.getCode();
    }

}
