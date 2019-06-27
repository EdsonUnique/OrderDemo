package edson.wechatfood;

import edson.wechatfood.security.SellerAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WechatfoodApplication implements WebMvcConfigurer {

    @Autowired
    private SellerAuth sellerAuth;

    public static void main(String[] args) {
        SpringApplication.run(WechatfoodApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathList=new ArrayList<String>();

        pathList.add("/buyOrder/*");
        pathList.add("/buyProduct/*");
        pathList.add("/product/*");
        pathList.add("/sellOrder/*");
        pathList.add("/seller/logout");

        registry.addInterceptor(sellerAuth).addPathPatterns(pathList);
    }
}
