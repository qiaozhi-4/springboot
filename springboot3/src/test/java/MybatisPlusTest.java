import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zking.App3;
import com.zking.entity.User;
import com.zking.mapper.IUserMapper;
//import com.zking.service.IUserService;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App3.class)
@RunWith(SpringRunner.class)
public class MybatisPlusTest
{
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUserService userService;
    
    @Test
    public void test3()
    {
        userMapper.selectList(null).forEach(System.out::println);
    }
    
    @Test
    public void test4()
    {
        userMapper.findAll().forEach(System.out::println);
    }
    
    @Test
    public void test5()
    {
        userService.transaction(1, 2, 100);
    }
}
