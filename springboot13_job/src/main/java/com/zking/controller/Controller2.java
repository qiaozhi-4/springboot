package com.zking.controller;

import com.alibaba.fastjson.JSONObject;
import com.zking.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    //回调路径
    @RequestMapping("/getCode")
    public String getCode(String code){
        // 要发送的数据对象
        Token token = new Token();
        token.setCode(code);
        // 请求地址
        String url = "https://gitee.com/oauth/token?grant_type="+ token.getGrantType()+"&code="
                +token.getCode()+"&client_id="+token.getClientId()+"&redirect_uri="
                +token.getRedirectUrl()+"&client_secret="+token.getClientSecret();
        // 发送post请求，并输出结果
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, token, String.class);
        String body = responseEntity.getBody(); // 获取响应体
        //转json
        JSONObject jsonobject = JSONObject.parseObject(body);
        //转map，并拿出指定key的值(令牌)
        String accessToken = (String) jsonobject.getInnerMap().get("access_token");
        System.out.println(accessToken);

        //发送验证请求，并获取第三方账号信息
        url = "https://gitee.com/api/v5/user?access_token=" + accessToken;
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
        return "index";
    }

}
