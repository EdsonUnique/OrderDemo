package edson.wechatfood.dao;

import edson.wechatfood.Entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 数据持久层采用spring-data-jpa
 * 继承JpaRepository接口 其中ProductCategory为实体类 Integer表示实体类主键类型
 */


public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
    //已封装好相关sql操作，直接使用
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
}
