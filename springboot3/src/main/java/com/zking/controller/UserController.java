package com.zking.controller;

import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.digest.HmacUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController = @ResponseBody + @Controller
@RestController
//自动生成无参带参构造
@RequiredArgsConstructor
public class UserController {
    private final IUserMapper mapper;
    private final IUserService userService;

    //极简验证
    @RequestMapping("/validate")
    @ResponseBody
    public Result<Void> validate(@RequestParam("lot_number") String lotNumber,
                                 @RequestParam("captcha_output") String captchaOutput,
                                 @RequestParam("pass_token") String passToken,
                                 @RequestParam("gen_time") String genTime, HttpSession session) {
        // 3.使用用户当前完成验证的流水号生成签名
        String signToken = new HmacUtils(HmacAlgorithms.HMAC_SHA_256,
                "f5e7386d87aa510914a7d42fd113007e").hmacHex(lotNumber);
        // 4.上传校验参数到极验二次验证接口, 校验用户验证状态
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lot_number", lotNumber);
        queryParams.add("captcha_output", captchaOutput);
        queryParams.add("pass_token", passToken);
        queryParams.add("gen_time", genTime);
        queryParams.add("sign_token", signToken);
        // captcha_id参数建议放在url后面, 方便请求异常时可以在日志中根据id快速定位到异常请求
        String url = String.format("%s/validate?captcha_id=%s",
                "https://gcaptcha4.geetest.com", "bb962a9d4859644b981346830e3f2172");
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String reason = "未知错误";
        session.setAttribute("validate", false);
        // 发起请求，注意处理接口异常情况，当请求极验二次验证接口异常时做出相应异常处理
        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity =
                    new HttpEntity<>(queryParams, headers);
            ResponseEntity<String> response =
                    client.exchange(url, HttpMethod.POST, requestEntity, String.class);
            JSONObject jsonObject = new JSONObject(Integer.parseInt(response.getBody()));
            // 5.根据极验返回的用户验证状态, 业务逻辑自行处理
            if ("success".equals(jsonObject.getString("result"))) {
                // 已经验证通过，添加validate信息
                session.setAttribute("validate", true);
                return Result.success(null);
            }
            if (jsonObject.getString("reason") != null) {
                reason =
                        jsonObject.getString("reason");
            }
        } catch (Exception e) {
            reason = "Api请求失败，请检查服务器网络或者用户参数配置 ";
        }
        return Result.fail(reason);
    }

    //登录
    @PostMapping("/loginAjax")
    @ResponseBody
    public Result<String> loginAjax(@ModelAttribute("validate") boolean validate,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password) {
        // 检查session中是否已经验证：（代码改成自己的逻辑！！！）
        if (!validate) {
            return Result.fail("login failed: no validate info!");
        }
        // 使用shiro正常处理登录逻辑：
        if (username.equals("admin") && password.equals("123")) {
            return Result.success("adminlogined!");
        }
        return Result.fail(username + "login failed: " + password);
    }

    @PostMapping("login")
    public void login() {

    }


    @PostMapping("register")
    public User register(String username, String password) {
        return userService.register(username, password);
    }

    @PutMapping("update")
    public boolean update(User user) {
        return userService.updateById(user);
    }

    @DeleteMapping("delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    @RequestMapping("users")
    public List<User> users() {
        return mapper.findAll();
    }

    @GetMapping("user/{id}")
    public User user(@PathVariable("id") int id) {
        return mapper.findById(id);
    }

    /*
    SpringBoot文件上传：
    1. 配置：spring.servlet.multipart.xxx=yyy
    2. Servlet中Part类型可以在SpringBoot中使用MultipartFile
    3. @PostMapping = @RequestMapping(path = "xxx", method = RequestMethod.POST)
        @GetMapping = @RequestMapping(path = "xxx", method = RequestMethod.GET)
     */
    // @RequestMapping(value = "upload", method = RequestMethod.POST)
    @PostMapping("upload")
    public Map<String, Object> upload(String username,
                                      @RequestParam MultipartFile file) throws IOException {
        // 可以直接获取文件名，并且一行代码将文件保存到指定位置
        File image = new File("D:\\user\\train\\java\\springboot\\springboot3\\src\\main\\resources\\static",
                file.getOriginalFilename());
        file.transferTo(image);
        // 返回
        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("file", file.getOriginalFilename());
        return map;
    }
}
