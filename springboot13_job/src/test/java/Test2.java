import com.zking.App13_job;
import com.zking.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = App13_job.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test2 {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test1() {
        //请求地址
        String client_id = "2694c10f42c9d33d9257e5693bcd5183506ee62e5b00396e13e8e5b80d882048";
        String redirect_uri = "https://zh.moegirl.org.cn/Fate%E7%B3%BB%E5%88%97";
        String url = "https://gitee.com/oauth/authorize?client_id="
                + client_id+"&redirect_uri="+redirect_uri+"&response_type=code";

        //发起请求,直接返回对象
        String s = restTemplate.getForObject(url, String.class);
        System.out.println(s);
    }

    @Test
    public void test2() {
        //请求地址
        String client_id = "2694c10f42c9d33d9257e5693bcd5183506ee62e5b00396e13e8e5b80d882048";
        String redirect_uri = "https://zh.moegirl.org.cn/Fate%E7%B3%BB%E5%88%97";
        String client_secret = "6d046cc842f9e1924566d1c16e46350f4968354a98f339784ef6395bd6577f4d";

        String url = "https://gitee.com/oauth/authorize?client_id="
                + client_id+"&redirect_uri="+redirect_uri+"&response_type=code";
        restTemplate.getForObject(url, String.class);

        url ="https://gitee.com/oauth/token?grant_type=authorization_code&code=1hhcdjh&client_id=" +
                client_id +
                "&redirect_uri=" +
                redirect_uri +
                "&client_secret=" + client_secret;

        //发起请求,直接返回对象
        System.out.println(restTemplate.postForObject(url,null, String.class));
    }





}
