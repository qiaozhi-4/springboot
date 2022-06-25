import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.App3;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App3.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class JobTest
{
    @Autowired
    private IUserMapper userMapper;
    
    @Test
    public void test1()
    {
        User user = new User();
        user.setUsername("111");
        user.setPassword("123");
        user.setMoney(100.0);
        userMapper.insert(user);
        System.out.println(user);
    }
    
    @Test
    public void test2()
    {
        PageHelper.startPage(2, 3);
        List<User> users = userMapper.selectList(null);
        PageInfo<User> info = new PageInfo<>(users);
        System.out.println(info.getTotal() + ", " + info.getPages());
        users.forEach(System.out::println);
    }
}
