import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zking.App10;
import com.zking.entity.User;
import com.zking.service.IUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App10.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test1 {

    @Autowired
    private IUserService userService;


    @Test
    public void test1() {
        System.out.println(userService.getOne(new QueryWrapper<User>().eq("username", "admin")));
    }

    @Test
    public void test2() {
        ByteSource salt = ByteSource.Util.bytes("test");//盐值
        //参数：加密名称，加密数值，加密盐值，迭代次数
        SimpleHash hash = new SimpleHash("MD5", "123", salt, 2);
        System.out.println(hash);
    }

    @Test
    public void test3() {
        System.out.println(userService.getRoleById(3));
    }


}
