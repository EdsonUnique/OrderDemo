package edson.wechatfood.dao;

import edson.wechatfood.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderItemDao extends JpaRepository<OrderItem,String> {

    List<OrderItem> findByOrderId(String orderId);

}
