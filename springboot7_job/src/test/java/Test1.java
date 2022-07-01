import com.zking.AppJob7;
import com.zking.entity.User;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = AppJob7.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test1 {

    @Autowired
    private IUserService userService;


    //  1) 编写根据ID查询指定用户的方法并缓存
    @Test
    public void test1() {
        System.out.println(userService.findById(1));
        System.out.println(userService.findById(1));
    }

    //	2) 编写分页查询，指定页码和每页数量，缓存到Redis（key可以为：数量#页码）
    @Test
    public void test2() {
        userService.findPage(2,3).getList().forEach(System.out::println);
        userService.findPage(2,3).getList().forEach(System.out::println);
    }

    //  3) 编写根据方法插入新的用户并缓存，同时清空分页缓存数据
    @Test
    public void test3() {
        System.out.println(userService.addUser(new User(null,"kk","123",10.0)));
    }

    //	4) 编写根据ID更新用户的方法，并清空分页缓存数据
    @Test
    public void test4() {
        System.out.println(userService.updateByIdUser(new User(999,"jj",null,10.0)));
    }





}
