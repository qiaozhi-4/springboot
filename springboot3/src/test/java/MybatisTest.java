import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.App3;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

// @ContextConfiguration(classes = App3.class) 用于SSM框架
@SpringBootTest(classes = App3.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class MybatisTest
{
    @Autowired
    private IUserMapper userMapper;
    
    @Test
    public void test1()
    {
        userMapper.findAll().forEach(System.out::println);
    }
    
    @Test
    public void test2()
    {
        PageHelper.startPage(2, 3);
        List<User> users = userMapper.findAll();
        PageInfo<User> info = new PageInfo<>(users);
        System.out.println(info.getTotal() + ", " + info.getPages());
        users.forEach(System.out::println);
    }
}
