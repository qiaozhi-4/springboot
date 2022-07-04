import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {
    @Test
    public void test1(){

        // 获取类加载器（几种方法）
        ClassLoader loader = Main.class.getClassLoader();
        loader = this.getClass().getClassLoader();
        loader = getClass().getClassLoader().getParent();
        loader = Thread.currentThread().getContextClassLoader();

        // 接口类型（必须是接口，可以是多个）
        Class<?>[] interfaces = {UserDao.class};

        // InvocationHandler对象
        InvocationHandler handler = new MyInvocationHandler();

        // Proxy.newProxyInstance可以创建一个UserDao对象
        UserDao userDao = (UserDao) Proxy.newProxyInstance(loader, interfaces, handler);
        // 可以随意正常使用这个对象了：
        System.out.println(userDao.findAll());
        System.out.println(userDao.findById());
    }
}
