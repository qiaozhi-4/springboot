package com.zking.controller;

import com.zking.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Controller1 {

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 登录提交 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/login")
    public String login(String username, String password, RedirectAttributes redirectAttributes) {
        try {
            //获取主体，任意地方都能获取
            Subject subject = SecurityUtils.getSubject();
            //传入需要验证的用户，以及令牌（密码）
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            //验证登录，会进入到核心类Realm验证
            //验证成功继续运行，失败则抛出下面的异常
            subject.login(token);
            //获取Realm里面设置的主体
            User user = (User) subject.getPrincipal();
            //把主体设置到当前会话里面

            subject.getSession().setAttribute("user", user);
            //转发到登录成功
            return "redirect:/success";
            //验证失败就抛出不同的异常
        } catch (UnknownAccountException e) {
            redirectAttributes.addFlashAttribute("error", "无此用户");
        } catch (IncorrectCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "密码不正确");
        } catch (LockedAccountException e) {
            redirectAttributes.addFlashAttribute("error", "用户已经被锁定");
        } catch (ExcessiveAttemptsException e) {
            redirectAttributes.addFlashAttribute("error", "尝试次数过多");
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("error", "认证错误");
        }
        //重定向到登录失败页面
        return "redirect:/error1";
    }

    @GetMapping("/error1")
    public String error(){
        return "error";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/shiro")
    public String shiro(){
        return "shiro";
    }

    @GetMapping("/403")
    public String unauthorized(){
        return "403";
    }

    //游客也可以访问
    @GetMapping("/t1")
    public String test1(Model model){
        model.addAttribute("info", "这个页面不需要登录");
        return "test";
    }

//    @RequiresPermissions("document:read")//注解这个路径，拥有某些特定权限
//    @RequiresGuest //不需要经过认证或者在原Session中存在记录可访问
//    @RequiresUser //必须为用户，比如登录或者记住我，和RequiresGuest刚好相反
    @RequiresAuthentication//注解这个路径，需要验证
    @GetMapping("/t2")
    public String test2(Model model){
        model.addAttribute("info", "这个页面需要登录");
        return "test";
    }

    @RequiresRoles({"admin"})//注解这个路径，需要特定角色
    @GetMapping("/t3")
    public String test3(Model model){
        model.addAttribute("info", "这个页面需要admin角色");
        return "test";
    }


}
