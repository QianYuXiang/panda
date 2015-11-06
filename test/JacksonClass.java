import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * Created by qianyuxiang on 15/11/2.
 */
public class JacksonClass {
    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    @JsonProperty("i")
    private Object inner;

    @JsonProperty("z")
    private List<Integer> z = null;

    @JsonProperty("a")
    private Map<String, String> a = null;

    @JsonIgnore
    private int _ignore = 0;

    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm:ss")
    private Date birthday = new Date();

    @JsonProperty("array")
    private String[] arr = new String[]{"aa", "bb"};

    protected abstract class _Inner {
        private int a;

        public _Inner(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    public class Inner extends _Inner{
        private int z;

        public Inner(int z) {
            super(z);
            this.z = z;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }

    public JacksonClass(int _x, int _y, int _z) {
        x = _x;
        y = _y;
        inner = new JacksonClass.Inner(_z);
        z = new ArrayList<Integer>();
        z.add(_x);
        z.add(_y);
        z.add(_z);

        this._ignore = -1;

        a = new HashMap<String, String>();
        a.put("name", "qyx");
        a.put("sex", "male");
        a.put("age", "25");
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object getInner() {
        return inner;
    }

    public void setInner(Inner inner) {
        this.inner = inner;
    }

    public List<Integer> getZ() {
        return z;
    }

    public void setZ(List<Integer> z) {
        this.z = z;
    }

    public Map<String, String> getA() {
        return a;
    }

    public void setA(Map<String, String> a) {
        this.a = a;
    }

    public int get_ignore() {
        return _ignore;
    }

    public void set_ignore(int _ignore) {
        this._ignore = _ignore;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return String.format("x=%d y=%d", x, y);
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }
}
