package edson.wechatfood.service;

import edson.wechatfood.Entity.Order;
import edson.wechatfood.VO.OrderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductOrderService {

    //创建订单
    Order createOrder(OrderVO orderVO);

    //删除订单
    Order deleteOrder(Order order);

    //查询订单
    Page<Order> findAll(String openid,Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    OrderVO findByOrderId(String orderId);

    //取消订单
    Order cancelOrder(String orderId);

    //完结订单
    Order finishOrder(String orderId);

    //支付订单
    Order payOrder(String orderId);
}
