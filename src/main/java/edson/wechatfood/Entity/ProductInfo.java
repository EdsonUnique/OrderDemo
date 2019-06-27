package edson.wechatfood.Entity;

import edson.wechatfood.enums.ProductStatus;
import edson.wechatfood.utils.GetPageEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="product")
public class ProductInfo {

    @Id
    @Column(name="id")
    private String productId;

    @Column(name="name")
    private String productName;

    @Column(name="price")
    private BigDecimal productPrice;

    @Column(name="stock")
    private Integer productStock;

    @Column(name="description")
    private String productDescription;

    /*
    商品图片的链接地址
     */
    @Column(name="icon")
    private String productIcon;

    @Column(name="category_type")
    private Integer categoryType;

    @Column(name="status")
    private Integer productStatus;

    public String GetProductStatusMsg(){
        return GetPageEnum.getPageEnum(productStatus, ProductStatus.class).getMessage();
    }


}
