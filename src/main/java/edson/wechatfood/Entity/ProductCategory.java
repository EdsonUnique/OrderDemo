package edson.wechatfood.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity//映射为实体
@Data//lombok的使用，还有@Get,@Set注解
@Table(name="category")
public class ProductCategory {

    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增类型
    @Column(name="id")
    private Integer categoryId;

    @Column(name="name")
    private String categoryName;
    @Column(name="type")
    private Integer categoryType;

}
