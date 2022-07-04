import com.zking.entity.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class MyInvocationHandler implements InvocationHandler {

    private final Object object = new Object();

    // proxy代理对象；method调用的方法；args调用方法的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("你在调用方法：" + method.getName());
        System.out.println("\t携带的参数：" + Arrays.toString(args));
        switch (method.getName()){
            case "findAll":
                return new ArrayList<User>();
            case "findById":
                return new User(0,"kk","55",10.0);
        }
        // 其他方法都使用默认的object去调用：
        return method.invoke(object, args);
    }
}
