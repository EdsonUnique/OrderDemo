package edson.wechatfood.dao;

import edson.wechatfood.Entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {

}
