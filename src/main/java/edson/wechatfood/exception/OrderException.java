package edson.wechatfood.exception;

import lombok.Getter;

public class OrderException extends RuntimeException {

    /**
     * 异常码
     */
    @Getter
    private Integer code;

    public OrderException(OrderExceptionEnum orderExceptionEnum){
        super(orderExceptionEnum.getMessage());
        this.code=orderExceptionEnum.getCode();
    }

    public OrderException(Integer code,String message){
        super(message);
        this.code=code;
    }

}
