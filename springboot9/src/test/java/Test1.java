import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zking.App9;
import com.zking.entity.User;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App9.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test1 {

    @Autowired
    private IUserService userService;


    @Test
    public void test1() {
        System.out.println(userService.getOne(new QueryWrapper<User>().eq("username", "admin")));
    }




}
