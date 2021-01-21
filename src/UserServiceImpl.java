
public class UserServiceImpl implements UserService{
    @Override
    public void saveUser(String name) {
        System.out.println("保存用户信息:" + name);
    }

    @Override
    public void updateUser(String name) {
        System.out.println("更新用户信息:" + name);
    }
}
