import java.lang.reflect.Method;


public class MyInvocationHandler {
    private UserServiceImpl userServiceImpl;

    public MyInvocationHandler(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    public Object invoke(Method method, Object[] args) throws Throwable {
        System.out.println("开启事务");
        method.invoke(userServiceImpl,args);
        System.out.println("提交事务");
        return null;
    }
}
