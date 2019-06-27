package edson.wechatfood.service;

import edson.wechatfood.Entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductInfoService {

    /**
     * 分页查询所有商品
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 根据商品id查询
     * @param id
     * @return
     */
    ProductInfo selectProductById(String id);

    /**
     * 根据库存查询
     * @param stock
     * @return
     */
    List<ProductInfo> findProductByStock(Integer stock);

    /**
     * 根据商品状态查询
     * @param productStatus
     * @return
     */
    List<ProductInfo> findProductByProductStatus(Integer productStatus);

    /**
     * 保存
     * @param productInfo
     * @return
     */
    ProductInfo saveOne(ProductInfo productInfo);



}
