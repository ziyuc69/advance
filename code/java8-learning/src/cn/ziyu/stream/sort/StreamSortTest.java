package cn.ziyu.stream.sort;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * @author wangxingang
 * @date 2021/6/11 16:25
 */
public class StreamSortTest {

    public void sorted() {
        //1. String 类自身已实现Compareable接口
        List<String> list = Arrays.asList("aa", "ff", "dd");
        // aa dd ff
        list.stream().sorted().forEach(System.out::println);

        //2. 自定义排序：先按姓名升序，姓名相同则按年龄升序
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 30);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);
        studentList.stream().sorted(
                (o1, o2) -> {
                    if (o1.getName().equals(o2.getName())) {
                        return o1.getAge() - o2.getAge();
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        ).forEach(System.out::println);
    }

    public static void main(String[] args) {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 30);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);
        Map<String, Set<Integer>> map = studentList.stream()
                .collect(groupingBy(Student::getName,
                        mapping(Student::getAge, toSet())));
        System.out.println(map);

        List<Integer> ages = map.values().stream().flatMap(Collection::stream).sorted().collect(toList());
        System.out.println(ages);
    }
}
