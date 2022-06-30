
import com.zking.App7;
import com.zking.service.MyService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App7.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class Test {
    @Autowired
    private MyService myService;

    @org.junit.Test
    public void test1() {
        myService.findAll().forEach(System.out::println);
    }

    @org.junit.Test
    public void test2() {
        System.out.println(myService.findById(1));
    }

    @org.junit.Test
    public void test3() {
        System.out.println(myService.findById(1));
        System.out.println(myService.findById(2));
        System.out.println(myService.findById(3));
    }


}
