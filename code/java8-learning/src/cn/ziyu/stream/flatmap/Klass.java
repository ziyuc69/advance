package cn.ziyu.stream.flatmap;

/**
 * @author wangxingang
 * @date 2021/6/11 16:16
 */
public class Klass {
    private final int field;

    public Klass(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "field=" + field;
    }
}
