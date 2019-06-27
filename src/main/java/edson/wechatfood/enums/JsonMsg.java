package edson.wechatfood.enums;


import lombok.Data;

public enum JsonMsg {

    SUCCESS(1,"成功"),
    FAILURE(0,"错误");

    private int code;

    private String msg;

    private  JsonMsg(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
