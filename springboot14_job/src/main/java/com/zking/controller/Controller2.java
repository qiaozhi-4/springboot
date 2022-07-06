package com.zking.controller;

import com.alibaba.fastjson.JSONObject;
import com.zking.entity.GitOAuth2User;
import com.zking.entity.Token;
import com.zking.entity.User;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;

//注释这个类下所有映射路径都能直接访问
@PermitAll
@Controller
@RequiredArgsConstructor
@RequestMapping
public class Controller2 {

    private final RestTemplate restTemplate;
    private final PasswordEncoder encoder;

    @RequestMapping("/getCode")
    public String auth(Model model,
                       @RegisteredOAuth2AuthorizedClient("gitee") OAuth2AuthorizedClient client,
                       @AuthenticationPrincipal OAuth2User user) {
        System.out.println("我来了");
// 第三方的配置信息
        ClientRegistration reg = client.getClientRegistration();
        model.addAttribute("client", reg.getClientName());

        model.addAttribute("name", user.getName());
        model.addAttribute("attributes", user.getAttributes());
// 详细信息（自定义类）
        GitOAuth2User giteeUser = (GitOAuth2User) user;
        model.addAttribute("username", giteeUser.getLogin());
        return "success";
    }

    //回调路径
//    @RequestMapping("/getCode")
    public String getCode(String code){
        // 要发送的数据对象
        Token token = new Token();
        token.setCode(code);
        // 请求地址
        String url = "https://gitee.com/oauth/token?grant_type="+ token.getGrantType()+"&code="
                +token.getCode()+"&client_id="+token.getClientId()+"&redirect_uri="
                +token.getRedirectUrl()+"&client_secret="+token.getClientSecret();
        // 发送post请求，通过验证码获取令牌
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, token, String.class);
        //转json
        JSONObject jsonobject = JSONObject.parseObject(responseEntity.getBody());
        //转map，并拿出指定key的值(令牌)
        String accessToken = (String) jsonobject.getInnerMap().get("access_token");
        System.out.println(accessToken);

        //发送验证请求，并获取第三方账号信息
        url = "https://gitee.com/api/v5/user?access_token=" + accessToken;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        //转json
        jsonobject = JSONObject.parseObject(forEntity.getBody());
        //获取响应体的id以及name
        Integer id = (Integer) jsonobject.get("id");
        String name = (String) jsonobject.get("name");
        System.out.println(id);
        System.out.println(name);
        String password = encoder.encode(id.toString());

        //查询数据库是否有这个用户
        User user = new User();

        String reqBode = JSONObject.toJSONString(user);
        //发送登录请求，使用security认证
        String s = restTemplate.postForObject("/login",reqBode,String.class);
        return "index";
    }

}
