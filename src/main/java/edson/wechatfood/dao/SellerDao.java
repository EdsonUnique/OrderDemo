package edson.wechatfood.dao;

import edson.wechatfood.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerDao extends JpaRepository<Seller,String> {

    Seller findByOpenid(String openid);

}
