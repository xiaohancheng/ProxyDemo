
public class StaticProxy2 implements UserService{
    private MyInvocationHandler myInvocationHandler;

    public StaticProxy2(MyInvocationHandler myInvocationHandler){
        this.myInvocationHandler = myInvocationHandler;
    }

    @Override
    public void saveUser(String name) throws Throwable{
        myInvocationHandler.invoke(UserService.class.getMethod("saveUser", String.class),new Object[]{name});
    }

    @Override
    public void updateUser(String name) throws Throwable{
        myInvocationHandler.invoke(UserService.class.getMethod("updateUser", String.class),new Object[]{name});
    }

}
