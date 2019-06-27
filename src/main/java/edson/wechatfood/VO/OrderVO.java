package edson.wechatfood.VO;

import edson.wechatfood.Entity.Order;
import edson.wechatfood.Entity.OrderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于值传递的对象
 */
@Data
public class OrderVO extends Order {

    List<OrderItem> orderItemList =new ArrayList<OrderItem>();
}
