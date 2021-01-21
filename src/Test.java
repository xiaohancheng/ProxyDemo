import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) throws Throwable {
        //test1();
        //test2();
        test3();
        //test4();
    }

    private static void test4() throws Throwable {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),userServiceImpl.getClass().getInterfaces(),new MyInvocationHandler1(userServiceImpl));
        userService.saveUser("李四");
        userService.updateUser("王五");
    }

    private static void test3() throws Throwable {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = (UserService) MyProxy.newProxyInstance(new MyClassLoader(),userServiceImpl.getClass().getInterfaces(),new MyInvocationHandler(userServiceImpl));
        userService.saveUser("懒洋洋");
        userService.updateUser("喜洋洋");

    }

    private static void test2() throws Throwable {
        UserService userService = new StaticProxy2(new MyInvocationHandler(new UserServiceImpl()));
        userService.saveUser("小红");
        userService.updateUser("小李");
    }

    private static void test1() throws Throwable {
        UserService userService = new StaticProxy1(new UserServiceImpl());
        userService.saveUser("小明");
        userService.updateUser("小王");
    }
}
