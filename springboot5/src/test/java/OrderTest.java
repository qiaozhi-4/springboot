import com.zking.App5;
import com.zking.entity.Order;
import com.zking.service.IOrderService;
import com.zking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App5.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class OrderTest
{
    @Autowired
    private IOrderService orderService;
    
    @Test
    public void test1(){
        orderService.findAll(null).getList().forEach(System.out::println);
    }
    
    @Test
    public void test2(){
        orderService.findByFuzzy("鱼",null).getList().forEach(System.out::println);
    }

    @Test
    public void test3(){
        Order order = new Order();
        order.setId(1);
        order.setAddress("岳麓区炎龙大厦1");
        System.out.println(orderService.updateOrderById(order));
    }

    @Test
    public void test4(){
        Order order = new Order();
        order.setUserId(2);
        order.setInfo("红烧排骨");
        order.setPrice(12.5);
        order.setAddress("岳麓区炎龙大厦1");
        System.out.println(orderService.addOrder(order));
    }

    @Test
    public void test5(){
        System.out.println(orderService.deleteOrderById(32));
    }
}
