package edson.wechatfood.enums;

import lombok.Getter;

public enum ProductStatus implements PageEnum{

    UP(1,"已上架"),//两个实例
    DOWN(0,"已下架");

    @Getter
    private Integer code;//两个属性

    @Getter
    private String message;

    //构造方法
    private ProductStatus(int code, String message){
        this.code=code;
        this.message=message;
    }

}
