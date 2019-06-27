package edson.wechatfood.service;

import edson.wechatfood.Entity.OrderItem;
import edson.wechatfood.VO.OrderItemVO;

import java.util.List;

/**
 * 订单项操作
 */
public interface ProductOrderItemService {

    OrderItem saveOrderItem(OrderItem orderItem);

    //修改订单项
    OrderItem updateOrderItem(OrderItem orderItem);

    //删除订单项
    OrderItem deleteOrderItem(OrderItem orderItem);

    //查询订单项
    List<OrderItem> findAllByOrderId(String orderId);

    OrderItem findByOrderItemId(String orderItemId);




}
