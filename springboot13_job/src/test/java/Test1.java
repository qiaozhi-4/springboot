import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zking.App13_job;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App13_job.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test1 {

    @Autowired
    private IUserService userService;


    @Test
    public void test1() {
        userService.getRolesByUserId(1).forEach(System.out::println);
    }

    @Test
    public void test2() {
        userService.getAuthoritySByUserId(3).forEach(System.out::println);
    }

    @Test
    public void test3() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }



}
