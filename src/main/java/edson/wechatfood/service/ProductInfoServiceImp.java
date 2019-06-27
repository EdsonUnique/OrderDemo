package edson.wechatfood.service;

import edson.wechatfood.dao.ProductInfoDao;
import edson.wechatfood.Entity.ProductInfo;
import edson.wechatfood.utils.IDStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductInfoServiceImp implements ProductInfoService {
    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo selectProductById(String id) {
        Optional<ProductInfo> productInfoOptional= productInfoDao.findById(id);
        return productInfoOptional.isPresent()?productInfoOptional.get():null;
    }

    @Override
    public List<ProductInfo> findProductByStock(Integer stock) {
        return null;
    }

    @Override
    public List<ProductInfo> findProductByProductStatus(Integer productStatus) {
        return null;
    }

    @Override
    @Transactional
    public ProductInfo saveOne(ProductInfo productInfo) {

        if(StringUtils.isEmpty(productInfo.getProductId())){
            //新增
            productInfo.setProductId(IDStrategy.generateID());
        }

        return productInfoDao.save(productInfo);
    }
}
