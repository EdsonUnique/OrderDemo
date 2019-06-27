package edson.wechatfood.service;

import edson.wechatfood.Entity.Order;
import edson.wechatfood.Entity.OrderItem;
import edson.wechatfood.Entity.ProductInfo;
import edson.wechatfood.VO.OrderItemVO;
import edson.wechatfood.VO.OrderVO;
import edson.wechatfood.controller.WebSocketService;
import edson.wechatfood.dao.ProductOrderDao;
import edson.wechatfood.enums.OrderStatus;
import edson.wechatfood.enums.PayStatus;
import edson.wechatfood.exception.OrderException;
import edson.wechatfood.exception.OrderExceptionEnum;
import edson.wechatfood.utils.IDStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderServiceImp implements ProductOrderService {

    @Autowired
    ProductOrderItemService productOrderItemService;

    @Autowired
    ProductOrderDao productOrderDao;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    WebSocketService webSocketService;

    @Override
    @Transactional//事务的注解 要么全部做 要么全部不做  出错将回滚
    public Order createOrder(OrderVO orderVO) {

        String orderID=IDStrategy.generateID();

        BigDecimal sum=new BigDecimal(0);
        //计算订单总价  商品单价只能从数据库中取出
        for(OrderItem orderItem :orderVO.getOrderItemList()){
            ProductInfo productInfo=productInfoService.selectProductById(orderItem.getProductId());
            //商品不存在
            if(null==productInfo){
                throw new OrderException(OrderExceptionEnum.PRODUCT_NOT_EXIST);
            }

            sum=productInfo.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity())).add(sum);

            //扣库存 库存不够抛异常
            if(productInfo.getProductStock()-orderItem.getProductQuantity()<0){
                throw new OrderException(OrderExceptionEnum.PRODUCT_SHORTAGE);
            }
            productInfo.setProductStock(productInfo.getProductStock()-orderItem.getProductQuantity());
            productInfoService.saveOne(productInfo);

            //填充订单表和订单详情表
            OrderItem new_orderItem=new OrderItem();
            BeanUtils.copyProperties(productInfo,orderItem);
            BeanUtils.copyProperties(orderItem,new_orderItem);
            new_orderItem.setOrderId(orderID);
            new_orderItem.setDetailId(IDStrategy.generateID());

            productOrderItemService.saveOrderItem(new_orderItem);

        }

        Order order=new Order();
        BeanUtils.copyProperties(orderVO,order);
        order.setOrderAmount(sum);
        order.setOrderId(orderID);

        //订单创建成功，发送websocket消息
        webSocketService.sendMessage("您有新订单！");

        return productOrderDao.save(order);
    }

    @Override
    public Order deleteOrder(Order order) {
        return null;
    }

    @Override
    public Page<Order> findAll(String openid,Pageable pageable) {
        //不关联查询订单详情，当查看订单详情时调用findByOrderId方法
        //openid 买家微信号
        Order order=new Order();//构造条件
        order.setPayStatus(null);
        order.setOrderStatus(null);
        order.setBuyerOpenId(openid);
        Example<Order> example=Example.of(order);
        Page<Order> orderPage=productOrderDao.findAll(example,pageable);
        return orderPage;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        //不关联查询订单详情，当查看订单详情时调用findByOrderId方法
        Page<Order> orderPage=productOrderDao.findAll(pageable);
        return orderPage;
    }

    @Override
    public OrderVO findByOrderId(String orderId) {

        Optional<Order> orderOptional=productOrderDao.findById(orderId);
        Order order=orderOptional.isPresent()?orderOptional.get():null;

        if(null==order){
            //订单不存在
            throw new OrderException(OrderExceptionEnum.PRODUCT_ORDER_NOT_EXIST);
        }

        List<OrderItem> orderItemList=productOrderItemService.findAllByOrderId(orderId);
        if(null==orderItemList || orderItemList.size()==0){
            throw new OrderException(OrderExceptionEnum.PRODUCT_ORDERITEM_NOT_EXIST);
        }

        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        orderVO.setOrderItemList(orderItemList);

        return orderVO;
    }

    @Override
    @Transactional
    public Order cancelOrder(String orderId) {

        //确定订单状态可以修改，若已完结订单则不能取消，若已付款则需退款
        OrderVO orderVO=findByOrderId(orderId);

        if(null==orderVO){
            throw new OrderException(OrderExceptionEnum.PRODUCT_ORDER_NOT_EXIST);
        }

        if(!orderVO.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            throw new OrderException(OrderExceptionEnum.ORDER_CANCLE_WRONG);
        }
        //已付款则退款
        if(orderVO.getPayStatus().equals(PayStatus.SUCCESS_PAY.getCode())){
            //退款
            //TODO
            //修改支付状态
        }

        //修改订单状态
        orderVO.setOrderStatus(OrderStatus.OLD_ORDER.getCode());
        //库存修改
        if(!orderVO.getOrderItemList().isEmpty()){
            orderVO.getOrderItemList().forEach((orderItem)->{
                ProductInfo productInfo=productInfoService.selectProductById(orderItem.getProductId());
                productInfo.setProductStock(productInfo.getProductStock()+orderItem.getProductQuantity());
                productInfoService.saveOne(productInfo);
            });
        }
        //写入数据库
        Order order=new Order();
        BeanUtils.copyProperties(orderVO,order);
        order=productOrderDao.save(order);
        if(null==order){
            //订单取消失败
            throw new OrderException(OrderExceptionEnum.ORDER_CANCLE_FAIL);
        }

        return order;
    }

    @Override
    @Transactional
    public Order finishOrder(String orderId) {

        //查询状态
        Optional<Order> orderOptional=productOrderDao.findById(orderId);
        Order order=orderOptional.isPresent()?orderOptional.get():null;
        if(null==order){
            throw new OrderException(OrderExceptionEnum.PRODUCT_ORDER_NOT_EXIST);
        }

        if(order.getPayStatus().equals(PayStatus.WAIT_PAY.getCode())){
            throw new OrderException(OrderExceptionEnum.PAY_STATUS_WRONG);
        }

        if(!order.getOrderStatus().equals(OrderStatus.FINISH_ORDER.getCode())){
            order.setOrderStatus(OrderStatus.FINISH_ORDER.getCode());
        }else{
            throw new OrderException((OrderExceptionEnum.ORDER_STATUS_WRONG));
        }

        //修改状态
        order=productOrderDao.save(order);
        if(null==order){
            throw new OrderException(OrderExceptionEnum.ORDER_FINISH_FAIL);
        }

        return order;
    }

    @Override
    @Transactional
    public Order payOrder(String orderId) {

        //判断支付状态
        Optional<Order> orderOptional=productOrderDao.findById(orderId);
        Order order=orderOptional.isPresent()?orderOptional.get():null;
        if(null==order){
            throw new OrderException(OrderExceptionEnum.PRODUCT_ORDER_NOT_EXIST);
        }

        if(!order.getPayStatus().equals(PayStatus.WAIT_PAY.getCode())){
            throw new OrderException(OrderExceptionEnum.ORDER_STATUS_WRONG);
        }

        //支付
        //TODO
        //修改支付状态
        order.setPayStatus(PayStatus.SUCCESS_PAY.getCode());

        order=productOrderDao.save(order);
        if(null==order){
            throw new OrderException(OrderExceptionEnum.ORDER_PAY_FAIL);
        }

        return order;
    }
}
