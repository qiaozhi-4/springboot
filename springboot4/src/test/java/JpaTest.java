
import com.zking.App4;
import com.zking.entity.User;
import com.zking.repository.IUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App4.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class JpaTest
{
    @Autowired
    private IUserRepository userRepository;
    
    @Test
    public void test1()
    {
        userRepository.findAll().forEach(System.out::println);
        System.out.println(userRepository.findUserById(1));

    }

    @Test
    public void test2()
    {
        User user = userRepository.findUserById(1);
        user.setId(null);
        user.setPassword("888");
        System.out.println(userRepository.save(user));

    }

    @Test
    public void test3()
    {
        System.out.println(userRepository.queryByUsernameInAndMoneyLessThanEqualOrderByIdDesc(
                new String[]{"qz", "admin"}, 100000.0, Sort.by("id")));
        System.out.println(userRepository.queryByUsernameInAndMoneyLessThanEqualOrderByIdDesc(
                new String[]{"qz", "admin"}, 100000.0));
    }

    @Test
    public void test4()
    {
        Pageable pageable = PageRequest.of(2,3,Sort.by("money"));
        userRepository.findUsersByMoneyIsLessThan(1000.0,pageable).forEach(System.out::println);
        userRepository.findUsersByMoneyIsGreaterThan(300.0,pageable).forEach(System.out::println);
    }
}
