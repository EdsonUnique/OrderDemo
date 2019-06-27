package edson.wechatfood.controller;

import edson.wechatfood.Entity.ProductCategory;
import edson.wechatfood.Entity.ProductInfo;
import edson.wechatfood.form.ProductForm;
import edson.wechatfood.service.ProductCategoryService;
import edson.wechatfood.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "pagenum",defaultValue = "1")Integer pagenum,
                       @RequestParam(value = "size",defaultValue = "1")Integer size,
                       Model model){
        PageRequest pageRequest=PageRequest.of(pagenum-1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        model.addAttribute("currentPage",pagenum);
        model.addAttribute("modelPage",productInfoPage);

        return "product/list";
    }

    /**
     * 页面跳转
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/addOrUpdateUI")
    public String addOrUpdateUI(@RequestParam(value = "productId",required = false)String productId,
                                Model model){

        try{
            List<ProductCategory> productCategoryList=productCategoryService.findAllProductCategory();
            model.addAttribute("productCategoryList",productCategoryList);
            if(!StringUtils.isEmpty(productId)){
                //修改操作
                ProductInfo productInfo = productInfoService.selectProductById(productId);
                model.addAttribute("product",productInfo);
            }

            return "product/form";
        }catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
            model.addAttribute("errorUrl","/sell/sellOrder/view");
            return "/common/error";
        }

    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@Valid ProductForm productForm,
                      BindingResult bindingResult,
                      Model model){
        try{

            if(bindingResult.hasErrors()){
                model.addAttribute("formError",bindingResult.getFieldError().getDefaultMessage());
                return addOrUpdateUI(productForm.getProductId(),model);
            }

            if(StringUtils.isEmpty(productForm.getProductId())){
                //添加操作
                ProductInfo productInfo=new ProductInfo();
                BeanUtils.copyProperties(productForm,productInfo);
                productInfoService.saveOne(productInfo);
                model.addAttribute("msg","添加成功");
            }else{
                ProductInfo productInfo=productInfoService.selectProductById(productForm.getProductId());
                BeanUtils.copyProperties(productForm,productInfo);
                productInfoService.saveOne(productInfo);
                model.addAttribute("msg","修改成功");
            }

            return list(1,1,model);

        }catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
            model.addAttribute("errorUrl","/sell/sellOrder/view");
            return "/common/error";
        }
    }

}
