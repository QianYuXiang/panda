import com.pillow.panda.AdbUtils.Adb;
import com.pillow.panda.AdbUtils.EnvironmentError;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qianyuxiang on 15/10/30.
 */
public class Main {

    public static void main(String[] args) {
        Adb adb = new Adb();
        try {
            Map<String, String> _m = adb.devices();
            Adb.Version _ = adb.version();
            System.out.println( _.getMajorNumber() );
            System.out.println( _.getMinorNumber() );
            System.out.println( _.getBuildNumber() );

            int a = 1;
            System.out.println(a);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EnvironmentError environmentError) {
            environmentError.printStackTrace();
        }

    }

    public static void test(){
        Properties props=System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        String osArch = props.getProperty("os.arch"); //操作系统构架
        String osVersion = props.getProperty("os.version"); //操作系统版本
        String androidHome = System.getenv("ANDROID_HOME");

        List<String> a = new ArrayList<String>();
        a.addAll(Collections.<String>singletonList("devices"));
        List<String> b = new ArrayList<String>();
        b.add("123");b.add("456");
        a.addAll(b);

        Object[] sl = a.toArray();
        for(Object s : sl) {
            System.out.println(s);
        }
    }

    public static void test2() {
        String s = "Android Debug Bridge version 1.0.32";
        Pattern p = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
        Matcher m = p.matcher(s);
        int n = m.groupCount();
        boolean bFind = m.find();
        if(bFind) {
            String i1 = m.group(0);
            String i2 = m.group(1);
            String i3 = m.group(2);
            String i4 = m.group(3);

            System.out.println(i1);
            System.out.println(i2);
            System.out.println(i3);
            System.out.println(i4);
        }
    }

}
