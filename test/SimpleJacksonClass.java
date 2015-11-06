import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class SimpleJacksonClass {

    private int xxx_;
    private int yyy_;

    @JsonIgnore
    private String s;

    @JsonProperty("where")
    private String _;

    public void setX(int _) {
        xxx_ = _;
    }

    public int getX() {
        return xxx_;
    }

    public void setY(int _) {
        yyy_ = _;
    }

    public int getY() {
        return yyy_;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
