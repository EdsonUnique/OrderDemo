package edson.wechatfood.service;

import edson.wechatfood.dao.ProductCategoryDao;
import edson.wechatfood.Entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public Page<ProductCategory> findAllProductCategory(Pageable pageable) {

        return productCategoryDao.findAll(pageable);
    }

    @Override
    public List<ProductCategory> findAllProductCategory() {
        return productCategoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypes);
    }

    @Override
    public ProductCategory selectProductCategoryById(Integer id) {
        Optional<ProductCategory> productCategory= productCategoryDao.findById(id);

        return productCategory.isPresent()?productCategory.get():null;
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }
}
