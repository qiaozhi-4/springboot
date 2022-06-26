
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
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App4.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test1() {
        redisTemplate.opsForValue().set("springboot::s1", "springboot 666");
        System.out.println(redisTemplate.opsForValue().get("springboot::s1"));
        redisTemplate.opsForHash();
        redisTemplate.opsForList();
        redisTemplate.opsForSet();
        redisTemplate.opsForZSet();
    }

    @Test
    public void test2() {
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("springboot::s1");
        System.out.println(ops.get());
        ops.set("hhhhh");
        System.out.println(ops.get());
        ops.set("springboot 666");
        System.out.println(ops.get());
    }

}
