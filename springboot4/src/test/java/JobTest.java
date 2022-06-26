import com.zking.App4;
import com.zking.entity.Employee;
import com.zking.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App4.class) // 用于SpringBoot测试
@RunWith(SpringRunner.class) // 用于SSM框架和SpringBoot中
public class JobTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test1(){
        Employee employee = new Employee();
        employee.setAge(18);
        employee.setDepartment("java");
        employee.setSalary(20000.0);
        System.out.println(employeeService.addEmployee(employee));
    }

    @Test
    public void test2(){
        employeeService.pageAll(0,3).forEach(System.out::println);
    }

    @Test
    public void test3(){
        employeeService.delete(11);
    }

    @Test
    public void test4(){
        Employee employee = new Employee();
        employee.setId(9);
        employee.setAge(18);
        employee.setDepartment("css");
        employee.setSalary(2000000.0);
        System.out.println(employeeService.update(employee));
    }

    @Test
    public void test5(){
        System.out.println(employeeService.findById(1));
    }

    @Test
    public void test6(){
        employeeService.findByNameLike("%q%").forEach(System.out::println);
    }
}
