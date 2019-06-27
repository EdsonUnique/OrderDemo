package edson.wechatfood.controller;

import com.alibaba.fastjson.JSONArray;
import edson.wechatfood.Entity.Order;
import edson.wechatfood.Entity.OrderItem;
import edson.wechatfood.VO.OrderVO;
import edson.wechatfood.VO.ResultVO;
import edson.wechatfood.VO.ResultWrapper;
import edson.wechatfood.exception.OrderException;
import edson.wechatfood.exception.OrderExceptionEnum;
import edson.wechatfood.form.OrderForm;
import edson.wechatfood.service.ProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.IntStream.of;

/**
 * 订单
 */
@RestController
@RequestMapping("/buyOrder")
@Slf4j
public class BuyOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody OrderForm orderForm,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //校验出错
            log.error("参数错误："+bindingResult.getFieldError().getDefaultMessage());
            throw new OrderException(OrderExceptionEnum.ORDER_PARAM_WRONG.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        OrderVO orderVO=new OrderVO();
        orderVO.setBuyerOpenId(orderForm.getOpenid());
        orderVO.setBuyerPhone(orderForm.getPhone());
        orderVO.setBuyerAddress(orderForm.getAddress());
        orderVO.setBuyerName(orderForm.getName());

        //json转List
        List<OrderItem> orderItemList=JSONArray.parseArray(orderForm.getItems(), OrderItem.class);
        orderVO.setOrderItemList(orderItemList);

        Order order=productOrderService.createOrder(orderVO);

        Map<String,String> data=new HashMap<String,String>();
        data.put("orderId",order.getOrderId());

        return ResultWrapper.success(data);
    }

    //查看所有订单
    @GetMapping("/list")

    public ResultVO list(@RequestParam("openid")String openid,
                         @RequestParam(value = "pagenum",defaultValue = "0") Integer pagenum,
                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("【买家微信号不能为空】");
            throw new OrderException(OrderExceptionEnum.ORDER_PARAM_WRONG);
        }

        PageRequest pageRequest=PageRequest.of(pagenum,size);
        Page<Order> orderPage=productOrderService.findAll(openid,pageRequest);

        return ResultWrapper.success(orderPage.getContent());

    }

    //查看订单详情
    @GetMapping("/view_detail/{id}")
    public ResultVO viewOrderItems(@PathVariable("id") String orderId){

        OrderVO orderVO=productOrderService.findByOrderId(orderId);

        return ResultWrapper.success(orderVO.getOrderItemList());

    }

    //取消订单
    @GetMapping("cancle_order/{id}")
    public ResultVO cancleOrder(@PathVariable("id") String orderId){
        //TODO 改进 任意订单号都能访问
        Order order=productOrderService.cancelOrder(orderId);

        return ResultWrapper.success(order);
    }

    //支付订单

    //完结订单


}
