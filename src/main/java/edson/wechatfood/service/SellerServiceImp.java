package edson.wechatfood.service;

import edson.wechatfood.Entity.Seller;
import edson.wechatfood.dao.SellerDao;
import edson.wechatfood.utils.IDStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SellerServiceImp implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Override
    @Transactional
    public Seller saveOne(Seller seller) {

        if(StringUtils.isEmpty(seller.getSellerId())){
            //添加操作，若不为空则为修改操作
            seller.setSellerId(IDStrategy.generateID());
        }

        return sellerDao.save(seller);

    }

    @Override
    public Seller findOneByOpenid(String openid) {

        return sellerDao.findByOpenid(openid);
    }
}
