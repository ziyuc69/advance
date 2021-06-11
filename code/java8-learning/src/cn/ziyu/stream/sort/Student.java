package cn.ziyu.stream.sort;

/**
 * @author wangxingang
 * @date 2021/6/11 16:25
 */
public class Student {

    private String name;
    private Integer age;
    private Integer type;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student(String name, Integer age, Integer num) {
        this.name = name;
        this.age = age;
        this.type = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + type +
                '}';
    }
}
