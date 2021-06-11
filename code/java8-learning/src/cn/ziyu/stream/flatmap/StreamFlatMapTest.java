package cn.ziyu.stream.flatmap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxingang
 * @date 2021/6/11 16:16
 */
public class StreamFlatMapTest {
    public void flatMap() {
        List<KlassGroup> groupList = Arrays.asList(
                new KlassGroup(new Klass(1), new Klass(2), new Klass(3)),
                new KlassGroup(new Klass(4), new Klass(5), new Klass(6)),
                new KlassGroup(new Klass(7), new Klass(8), new Klass(9)),
                new KlassGroup(new Klass(10)));

        // 需要一个List<Klass>, 使用map却得到一个 List<List<Klass>>
        List<List<Klass>> lists = groupList.stream().map(KlassGroup::getKlassList).collect(Collectors.toList());
        System.out.println("map后的结果: " + lists);

        // 需要一个List<Klass>, 使用map得到一个 List<Klass>
        // flatMap完美解决 （需要说明的是，flatMap需要把每个list的每个元素转换为一个个独立的stream后，才可以收集）
        List<Klass> kLasses = groupList.stream().flatMap(g -> g.getKlassList().stream()).collect(Collectors.toList());
        System.out.println("flatMap后的结果: " + kLasses);
    }
}
