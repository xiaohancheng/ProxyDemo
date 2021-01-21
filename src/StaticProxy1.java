
public class StaticProxy1 implements UserService{
    private UserServiceImpl userServiceImpl;

    public StaticProxy1(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void saveUser(String name) {
        System.out.println("开启事务");
        userServiceImpl.saveUser(name);
        System.out.println("提交事务");
    }

    @Override
    public void updateUser(String name) {
        System.out.println("开启事务");
        userServiceImpl.updateUser(name);
        System.out.println("提交事务");
    }
}
