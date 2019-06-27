package edson.wechatfood.security;

import edson.wechatfood.exception.SellerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理
 */
@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(SellerException.class)
    public String handleLoginException(Exception e){
        if(e instanceof SellerException){
            e.printStackTrace();
            return "login";
        }
        return "";
    }


}
