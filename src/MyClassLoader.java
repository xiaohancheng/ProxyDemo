import java.io.*;

/**
 * 代码生成、编译、重新load到JVM中
 */
public class MyClassLoader extends ClassLoader{
    private File baseDir;
    public MyClassLoader() {
        //获取当前文件的所在路径
        String path = MyClassLoader.class.getResource("").getPath();
        this.baseDir=new File(path);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        if (baseDir!=null){
            //获取class文件，使用类加载器加载到JVM
            File classFile=new File(baseDir,className+".class");
            if (classFile.exists()){
                try (FileInputStream fileInputStream = new FileInputStream(classFile);
                     ByteArrayOutputStream outputStream=new ByteArrayOutputStream();) {
                    byte[] bytes=new byte[1024];
                    int len;
                    while ((len=fileInputStream.read(bytes))!=-1){
                        outputStream.write(bytes,0,len);
                    }
                    //装入class
                    return defineClass(className,outputStream.toByteArray(),0,outputStream.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    classFile.delete();//删除class文件
                }

            }
        }
        return super.findClass(className);
    }
}
