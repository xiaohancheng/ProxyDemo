import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;


public class MyProxy {
    private static String ln="\r\n";

    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h)
            throws IllegalArgumentException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //1、生成源代码
        String proxySrc=gengerateSrc(interfaces);

        //2、将生成的源代码输出到磁盘，保存为.java文件
        String fileNmae=MyProxy.class.getResource("").getPath();
        File f=new File(fileNmae+"$Proxy0.java");
        try {
            FileWriter fw=new FileWriter(f);
            fw.write(proxySrc);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3、编译源代码，并生成.class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> objects = manager.getJavaFileObjects(f);

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, objects);
        task.call();
        manager.close();
        //4、将.class文件中的内容，动态加载到JVM中来
        Class<?> $Proxy0 = loader.findClass("$Proxy0");//获得类
        //5、返回被代理的对象
        Constructor<?> constructor = $Proxy0.getConstructor(MyInvocationHandler.class);//获取构造方法
        f.delete();//删除java文件
        return constructor.newInstance(h);//实例化
    }

    //生成java文件,这是关键点，通过这里可以看出如何创建一个代理类
    private static String gengerateSrc(Class<?>[] interfaces){
        StringBuffer src=new StringBuffer();
        src.append("import java.lang.reflect.Method;\n" +ln);
        src.append("public final class $Proxy0  implements "+interfaces[0].getName()+" {"+ln);

        src.append("MyInvocationHandler myInvocationHandler;"+ln);

        src.append("public $Proxy0(MyInvocationHandler myInvocationHandler){"+ln);
        src.append("this.myInvocationHandler=myInvocationHandler;"+ln);
        src.append("}"+ln);

        for (Method method : interfaces[0].getMethods()) {
            src.append("public "+method.getReturnType().getName()+" "+method.getName()+" ("+ getMethodParams(method) +") " +
                    "throws Throwable{"+ln);
            src.append("Method m="+interfaces[0].getName()+".class.getMethod(\""+method.getName()+"\",String.class);"+ln);

            src.append("myInvocationHandler.invoke(m,new Object[]{"+ getMethodParamNames(method)+"});"+ln);
            src.append("}"+ln);
        }
        src.append("}");
        return src.toString();
    }

    private static String getMethodParamNames(Method method) {
        Parameter[] params = method.getParameters();
        List<String> paramterList = new ArrayList<>();
        for (Parameter parameter : params) {
            paramterList.add(parameter.getName());
        }
        return String.join(",",paramterList);
    }

    private static String getMethodParams(Method method) {
        String result ="";
        Parameter[] parameters = method.getParameters();
        if(parameters == null || parameters.length==0) return result;

        for (Parameter parameter : parameters) {
            result += parameter.getType().getName() + " " + parameter.getName() + ",";
        }
        return result.substring(0,result.length()-1);
    }


}
