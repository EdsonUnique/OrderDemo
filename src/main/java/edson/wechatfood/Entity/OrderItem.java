package edson.wechatfood.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 订单详情表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order_detail")
@DynamicUpdate
public class OrderItem {

    @Id
    @Column(name="detail_id")
    private String detailId;

    @Column(name="order_id")
    private String orderId;

    @Column(name="product_id")
    private String productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_price")
    private BigDecimal productPrice;

    @Column(name="product_quantity")
    private Integer productQuantity;

    @Column(name="product_icon")
    private String productIcon;

}
