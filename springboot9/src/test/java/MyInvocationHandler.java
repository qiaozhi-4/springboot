import com.zking.entity.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class MyInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        System.out.println(Arrays.toString(args));
        switch (method.getName()){
            case "findAll":
                return new ArrayList<User>();
            case "findById":
                return new User(0,"kk","55",10.0);
        }
        return null;
    }
}
