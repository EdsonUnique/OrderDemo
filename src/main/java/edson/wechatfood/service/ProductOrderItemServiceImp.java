package edson.wechatfood.service;

import edson.wechatfood.Entity.OrderItem;
import edson.wechatfood.VO.OrderItemVO;
import edson.wechatfood.dao.ProductOrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderItemServiceImp implements ProductOrderItemService{

    @Autowired
    ProductOrderItemDao productOrderItemDao;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return productOrderItemDao.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public OrderItem deleteOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public List<OrderItem> findAllByOrderId(String orderId) {

        List<OrderItem> orderItemList=productOrderItemDao.findByOrderId(orderId);

        return orderItemList;
    }

    @Override
    public OrderItem findByOrderItemId(String orderItemId) {
        return null;
    }
}
