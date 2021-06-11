package cn.ziyu.stream.flatmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangxingang
 * @date 2021/6/11 16:16
 */
public class KlassGroup {
    private final List<Klass> group = new ArrayList<>();

    public KlassGroup(Klass... objList) {
        this.group.addAll(Arrays.asList(objList));
    }

    public List<Klass> getKlassList() {
        return group;
    }
}
