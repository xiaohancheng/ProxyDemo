import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class MyInvocationHandler1 implements InvocationHandler {
    private UserServiceImpl userServiceImpl;

    public MyInvocationHandler1(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开启事务");
        method.invoke(userServiceImpl,args);
        System.out.println("提交事务");
        return null;
    }
}
