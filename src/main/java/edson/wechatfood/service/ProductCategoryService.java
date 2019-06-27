package edson.wechatfood.service;

import edson.wechatfood.Entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//业务逻辑层注解
public interface ProductCategoryService {

    /**
     * 分页查询所有的商品类别
     * @param pageable 分页对象
     * @return
     */
    Page<ProductCategory> findAllProductCategory(Pageable pageable);

    List<ProductCategory> findAllProductCategory();

    /**
     * 根据指定的类别id查询类别信息
     * @param categoryTypes
     * @return
     */
    List<ProductCategory>findByCategoryTypeIn(List<Integer> categoryTypes);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProductCategory selectProductCategoryById(Integer id);

    /**
     * 保存类别对象 并返回被影响的行数
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);


}
