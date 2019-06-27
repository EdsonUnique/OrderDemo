package edson.wechatfood.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 订单表单信息
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "买家姓名不能为空")
    private String name;

    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址不能为空")
    private String address;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "买家手机号不能为空")
    private String phone;

    /**
     * 买家微信id
     */
    @NotEmpty(message = "买家微信号不能为空")
    private String openid;

    /**
     * 买家的订单项
     */
    @NotEmpty(message = "买家的订单项不能为空")
    private String items;

}
