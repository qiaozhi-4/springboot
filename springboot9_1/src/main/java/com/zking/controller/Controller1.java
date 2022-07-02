package com.zking.controller;

import com.zking.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
//一级路径
@RequestMapping("")
//@ResponseBody
@Slf4j//相当于直接生成一个Log对象
@SessionAttributes({"user", "name"})
public class Controller1 {

    @PostMapping("/login")
    public String login(
            @RequestParam
            String username, // 必须携带的参数
            @RequestParam
            String password, // 必填
            Model model, RedirectAttributes redirectAttributes) {
        // 使用Shiro登录
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        try {
            // 登录
            subject.login(token);
            // 设置会话
            User principal = (User) subject.getPrincipal(); // 自定义的
            model.addAttribute("user", principal);
            model.addAttribute("name", principal.getUsername());
            // subject.getSession().setAttribute("user", principal);
            // subject.getSession().setAttribute("name", principal.getUsername());
            return "redirect:/success";
        } catch (UnknownAccountException e) {
            // 用户名不存在
            redirectAttributes.addFlashAttribute("error", "用户名不存在");
        } catch (LockedAccountException e) {
            // 锁定
            redirectAttributes.addFlashAttribute("error", "用户锁定");
        } catch (IncorrectCredentialsException e) {
            // 密码不对
            redirectAttributes.addFlashAttribute("error", "密码不对");
        } catch (ExcessiveAttemptsException e) {
            // 尝试次数过多
            redirectAttributes.addFlashAttribute("error", "尝试次数过多");
        } catch (AuthenticationException e) {
            // 其他异常
            redirectAttributes.addFlashAttribute("error", "其他登录异常");
        }
        return "redirect:/error1";
    }

    @GetMapping("/error1")
    public String error() {
        return "error";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }


}
