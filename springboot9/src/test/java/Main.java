import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {
    @Test
    public void test1(){

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = {UserDao.class};
        InvocationHandler handler = new MyInvocationHandler();
        UserDao userDao = (UserDao) Proxy.newProxyInstance(loader, interfaces, handler);
        System.out.println(userDao.findAll());
        System.out.println(userDao.findById());
    }
}
