package edson.wechatfood.service;

import edson.wechatfood.Entity.Seller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface SellerService {

    //创建一个用户
    @Transactional
    Seller saveOne(Seller seller);

    //根据openid查询用户
    Seller findOneByOpenid(String openid);

}
