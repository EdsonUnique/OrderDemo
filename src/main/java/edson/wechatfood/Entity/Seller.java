package edson.wechatfood.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 卖家用户信息
 */
@Entity
@Data
@Table(name="sell_info")
public class Seller {

    @Id
    @Column(name = "seller_id")
    private String sellerId;

    private String username;

    private String password;

    @Column(name="seller_openid")
    private String openid;


}
