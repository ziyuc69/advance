# MapStruct学习

### 简介

MpaStruct是一个可以生成类型安全bean映射类的java注解处理器。

### 安装

MapStruct是一个基于[JSR 269](http://www.jcp.org/en/jsr/detail?id=269) 规范的Java注解处理器，因此可以使用命令行构建或使用IDE构建，那我们就是用IDEA来玩吧！

> 基于Maven来安装，也就是在maven中引用

```java
    <properties>
        <org.mapstruct.version>1.4.1.Final</org.mapstruct.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${org.mapstruct.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source> <!-- depending on your project -->
                    <target>1.8</target> <!-- depending on your project -->
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${org.mapstruct.version}</version>
                        </path>
                        <!-- other annotation processors -->
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

> MapStructIDE的插件下载

MapStruct贴心提供了IDE的插件，用户快速生成bean的映射代码

MapStruct官网下载：[MapStruct IntelliJ IDEA plugin](https://mapstruct.org/news/2017-09-19-announcing-mapstruct-idea/)

jetbrains插件仓库下载：[MapStruct support for IntelliJ IDEA](https://plugins.jetbrains.com/plugin/10036-mapstruct-support)

> 其他安装方式，大家参考官方文档：[MapStruct官网](https://mapstruct.org/)

### 直奔主题

MapStruct还可以通过maven中的一些配置，来决定bean映射代码的生成策略，以及在Java9中还有一些新的特性，不过这些对于刚入手使用来说并不是重点，让我们快速开始吧！

> 定义一个Mapper

```java
@Mapper
public interface CarMapper {

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);
}
```

@Mapper：指定了基于这个接口来生成bean的映射代码；一般实现类是在接口类的后面加上impl；也就是CarMapperImpl类

@Mapping：一般进行两个bean之间的映射，难免会出现字段名称不匹配(也就是字段名称不一样)，但是代表的是同一个意思的情况；这个时候就需要手动指定，让他们在copy的时候，映射过去。

> 完善Mapper中使用到的bean

```java
public class Person {

    private String name;
	// 省略getter、setter方法
}

public class PersonDto {

    private String fullName;
    // 省略getter、setter方法
}

public class Car {

    private String make;
    private Integer numberOfSeats;
    // 省略getter、setter方法
}

public class CarDto {

    private String manufacturer;
    private Integer seatCount;
    // 省略getter、setter方法
}
```

> 获取mapper实例

当我们没有使用DI框架时，也就是没有spring，不是JavaEE开发；单纯的是JavaSE开发的时候，想要获取mapper实例，则需要通过调用org.mapstruct.factory.Mappers类中的getMapper方法来实现。

```java
CarMapper mapper = Mappers.getMapper(CarMapper.class);

// CarMapper其实只做bean的映射，因此没必要每次bean映射的时候，都创建一个实例
// 所有CarMapper可以定义为单利模式
@Mapper
public interface CarMapper {
    public static final CarMapper mapper = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);
}
```

> 执行bean的映射

```java
public class MapStructTest {
    public static void main(String[] args) {
        CarMapper instance = CarMapper.INSTANCE;
        Car car = new Car();
        car.setMake("丰田");
        car.setNumberOfSeats(10);

        CarDto carDto = instance.carToCarDto(car);
        System.out.println(carDto.getManufacturer() + "--" + carDto.getSeatCount());
    }
}
```

好了，至此MapStruct的简单demo就跑起来了，跑一下看到结果是这样正常映射过去的；由于企业开发主流还是JavaEE开发，需要借助sping容器来管理bean的，所有我们最好还是通过spring来管理这个单利的mapper吧！

```java
@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "name", target = "fullName")
    PersonDto personToPersonDto(Person person);
}
```

MapStruct针对DI注入有好三种方式：

cdi：使用@Inject注解注入

spring：使用@Autowired注解注入

jsr330：使用@Inject注解注入

因为我们企业开发基本都是依赖spring的，所有我们使用spring即可`@Mapper(componentModel = "spring")`；既然使用了spring的注入方式，那么每个mapper上面都去指定也挺烦的，而且都是重复的工作；这种我们能想到的问题，MapStruct怎么可能想不到，那么如何去优化呢？

> MapStruct的配置选项

MapStruct可以在maven中配置全局选项，让MapStruct在编译的时候安装这个全局配置生成bean的映射代码；那么怎么配置呢？

```java
    <build>
        <plugins>
            <plugin>
                // ... 无关的配置就不贴出来了
                    <compilerArgs>
                        <compilerArg>
                            -Amapstruct.defaultComponentModel=spring
                        </compilerArg>
                    </compilerArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

> 其他

快速入门仅仅说了普通bean的映射，集合的映射如何做？map的如何做呢？以及更高阶的玩法呢？

如果是要转换一个集合的话，只需要把这里的实体类换成集合就行了，例如：

```
List<UserVO1> toConvertVOList(List<User> source);
```

其实，上面的应该满足了基本需求！如果再搞定bean的组合嵌套通过mapper来映射，那就满足大部分需求了(因为如何bean定义合理的话，应该也就只有普通bean和List，以及bean的组合嵌套了)。

### 最后

MapStruct的快速入门到这里就结束了。虽然没有研究高级的东西，但是基本上可以在项目中用起来了。下一讲我们开始MapStruct的全面认识，以及一些高阶的用法。最后做个简单的小结：

1. MapStruct简单介绍

2. MapStruct基于maven的安装
3. 通过一个快速入门的例子介绍了MapStruct的使用
4. 介绍了MapStruct在spring中如何使用，这样我们在项目中就可以使用起来了