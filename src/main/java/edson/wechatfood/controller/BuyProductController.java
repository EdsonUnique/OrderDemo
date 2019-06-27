package edson.wechatfood.controller;

import edson.wechatfood.VO.CategoryVO;
import edson.wechatfood.VO.ProductVO;
import edson.wechatfood.VO.ResultVO;
import edson.wechatfood.VO.ResultWrapper;
import edson.wechatfood.Entity.ProductCategory;
import edson.wechatfood.Entity.ProductInfo;
import edson.wechatfood.service.ProductCategoryService;
import edson.wechatfood.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController//接收的请求数据是JSON格式
@RequestMapping("/buyProduct")
public class BuyProductController {

    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductInfoService productInfoService;

    /**
     * 查看所有类别及其商品，不包含商品的类别不显示
     * @return
     */
    @RequestMapping("/list")
    public ResultVO list(){
        ResultVO rv=new ResultVO();


        //查询数据
            //查询所有商品
        List<ProductInfo> productInfoList=new ArrayList<ProductInfo>();
        productInfoList= productInfoService.findAll(new PageRequest(0,1)).getContent();
            //查询所需的商品类型(至少含有一个商品)
        List<Integer> categoryTypes=new ArrayList<Integer>();

        //lambada的使用    (参数)->返回值或相关操作
        productInfoList.forEach(pi -> categoryTypes.add(pi.getCategoryType()));

        //传统方法遍历
//        for(ProductInfo pi:piList){
//            if(!categoryTypes.contains(pi.getCategoryType())){
//                categoryTypes.add(pi.getCategoryType());
//            }
//        }

        List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
        productCategoryList= productCategoryService.findByCategoryTypeIn(categoryTypes);

        //拼接成JSON格式
        List<CategoryVO> json_categoryList=new ArrayList<CategoryVO>();

        for(ProductCategory pc:productCategoryList){
            CategoryVO cv=new CategoryVO();
            BeanUtils.copyProperties(pc,cv);

            List<ProductVO> json_productList=new ArrayList<ProductVO>();
            for(ProductInfo pi:productInfoList){
                if(pi.getCategoryType().equals(pc.getCategoryType())){
                    ProductVO pv=new ProductVO();
                    BeanUtils.copyProperties(pi,pv);
                    json_productList.add(pv);
                }
            }

            cv.setProducts(json_productList);
            json_categoryList.add(cv);
        }

        return ResultWrapper.success(json_categoryList);
    }

}
