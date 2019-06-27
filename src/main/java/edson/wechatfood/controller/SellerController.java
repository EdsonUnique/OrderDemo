package edson.wechatfood.controller;

import edson.wechatfood.Entity.Seller;
import edson.wechatfood.constant.CookieConstant;
import edson.wechatfood.constant.RedisConstant;
import edson.wechatfood.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//redis操作类

    @PostMapping("/login")
    public String login(@RequestParam String openid,
                        HttpServletResponse response,
                        Model model){

        //根据openid查询mysql数据库中是否存在该用户
        Seller seller=sellerService.findOneByOpenid(openid);

        if(null==seller){
            return "login";//登录失败
        }

        //若存在，将用户Openid存入redis数据库，并设置过期时间
        String token_key= UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;

        stringRedisTemplate.opsForValue().set(RedisConstant.KEY_PREFIX+token_key,openid,expire, TimeUnit.SECONDS);

        //将redis数据库中对应的用户openid和key信息设置为token
        Cookie cookie=new Cookie(CookieConstant.TOKEN_KEY,RedisConstant.KEY_PREFIX+token_key);
        cookie.setMaxAge(RedisConstant.EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/sellOrder/view";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model){

        Cookie[] cookies=request.getCookies();
        if(null!=cookies && cookies.length>0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(CookieConstant.TOKEN_KEY)){
                    cookie.setMaxAge(0);//去除cookie 过期
                    stringRedisTemplate.opsForValue().getOperations().delete(cookie.getValue());
                    break;
                }
            }
        }

        return "redirect:/index/";
    }


}
