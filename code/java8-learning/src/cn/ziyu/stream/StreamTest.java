package cn.ziyu.stream;

import cn.ziyu.stream.flatmap.StreamFlatMapTest;
import cn.ziyu.stream.sort.StreamSortTest;
import cn.ziyu.stream.sort.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: wangxg
 * @date: 2019-11-06 20:16
 **/
public class StreamTest {
    public static void main(String[] args) throws Exception {
        // 创建流
        create();

        // 中间流
        operationStream();

        // map映射流
        mapStream();

        // flapMap映射流
        flatMapStream();

        // sorted排序
        sortStream();

        // peek/forEach消费
        consumerStream();

        // 终止流操作
        terminateStream();

        // 收集流
        collectorsStream();
    }

    private static void create() throws Exception {
        // 1（Collection -> stream）
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        Stream<String> parallelStream = list.parallelStream();

        // 2 (array -> stream)
        Integer[] nums = new Integer[10];
        Stream<Integer> arrayStream = Arrays.stream(nums);

        // 3 (Stream 中的静态方法)
        Stream<Integer> stream1 = Stream.of(1,2,3,4,5,6);
        stream1.forEach(System.out::println);

        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 2).limit(6);
        stream2.forEach(System.out::println);

        Stream<Double> stream3 = Stream.generate(Math::random).limit(2);
        stream3.forEach(System.out::println);

        // 4 读取文件转换为流
        BufferedReader reader = new BufferedReader(new FileReader("D:\\test_stream.txt"));
        Stream<String> lineStream = reader.lines();
        lineStream.forEach(System.out::println);

        // 5 字符串切割为流
        Pattern pattern = Pattern.compile(",");
        Stream<String> stringStream = pattern.splitAsStream("a,b,c,d");
        stringStream.forEach(System.out::println);
    }

    public static void operationStream() {
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        Stream<Integer> newStream = stream.filter(s -> s > 5) //6 6 7 9 8 10 12 14 14
                .distinct() //6 7 9 8 10 12 14
                .skip(2) //9 8 10 12 14
                .limit(2); //9 8
        newStream.forEach(System.out::println);
    }

    public static void mapStream() {
        List<String> list = Arrays.asList("a,b,c", "1,2,3");
        //将每个元素转成一个新的且不带逗号的元素
        Stream<String> s1 = list.stream().map(s -> s.replaceAll(",", ""));
        // abc 123
        s1.forEach(System.out::println);
    }

    /**
     * flatmap映射
     */
    private static void flatMapStream() {
        List<String> list = Arrays.asList("a,b,c", "1,2,3");
        Stream<String> s3 = list.stream().flatMap(s -> {
            //将每个元素转换成一个stream
            String[] split = s.split(",");
            return Arrays.stream(split);
        });

        // a b c 1 2 3
        s3.forEach(System.out::println);

        // 另一个例子请查看目录：cn.ziyu.stream.flatmap
        StreamFlatMapTest flatMapTest = new StreamFlatMapTest();
        flatMapTest.flatMap();
    }

    /**
     * 排序
     */
    private static void sortStream() {
        StreamSortTest sortTest = new StreamSortTest();
        sortTest.sorted();
    }

    /**
     * 消费
     */
    private static void consumerStream() {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);
        studentList.stream()
                .peek(o -> o.setAge(100))
                .forEach(System.out::println);
    }

    /**
     * 终止流
     */
    private static void terminateStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // false 全匹配
        boolean allMatch = list.stream().allMatch(e -> e > 10);
        // true 无匹配
        boolean noneMatch = list.stream().noneMatch(e -> e > 10);
        // true 任意一个匹配
        boolean anyMatch = list.stream().anyMatch(e -> e > 4);
        // 1 查找第一个元素
        Integer findFirst = list.stream().findFirst().get();
        // 1 获取任意一个
        Integer findAny = list.stream().findAny().get();
        // 5 总数
        long count = list.stream().count();
        // 5 最大
        Integer max = list.stream().max(Integer::compareTo).get();
        // 1 最小
        Integer min = list.stream().min(Integer::compareTo).get();
    }

    /**
     * 收集流
     */
    private static void collectorsStream() {
        Student s1 = new Student("aa", 10, 1);
        Student s2 = new Student("bb", 20, 2);
        Student s3 = new Student("cc", 10, 3);
        List<Student> list = Arrays.asList(s1, s2, s3);

        //转成list
        List<Integer> ageList = list.stream().map(Student::getAge).collect(Collectors.toList()); // [10, 20, 10]

        //转成set
        Set<Integer> ageSet = list.stream().map(Student::getAge).collect(Collectors.toSet()); // [20, 10]

        //转成map,注:key不能相同，否则报错
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {cc=10, bb=20, aa=10}

        //字符串分隔符连接
        String joinName = list.stream().map(Student::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)

        //【聚合操作】
        //1.学生总数
        Long count = list.stream().collect(Collectors.counting()); // 3
        //2.最大年龄 (最小的minBy同理)
        Integer maxAge = list.stream().map(Student::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
        //3.所有人的年龄总和
        Integer sumAge = list.stream().collect(Collectors.summingInt(Student::getAge)); // 40
        //4.平均年龄
        Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334

        // 带上以上所有方法
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
        System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());

        //分组
        Map<Integer, List<Student>> ageMap = list.stream().collect(Collectors.groupingBy(Student::getAge));

        //多重分组,先根据类型分再根据年龄分
        Map<Integer, Map<Integer, List<Student>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(Student::getType, Collectors.groupingBy(Student::getAge)));

        //分区
        //分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<Student>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));

        //规约
        Integer allAge = list.stream().map(Student::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40
    }
}
