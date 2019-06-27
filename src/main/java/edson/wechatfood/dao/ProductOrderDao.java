package edson.wechatfood.dao;

import edson.wechatfood.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderDao extends JpaRepository<Order,String> {



}
