import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.App5;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App5.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class UserTest
{
    @Autowired
    private IUserService userService;
    
    @Test
    public void test1(){
        System.out.println(userService.register("hhhh", "123"));
    }
    
    @Test
    public void test2(){
        System.out.println( userService.login("hhhh","123"));
    }
}
