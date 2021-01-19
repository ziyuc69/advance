

### 简介

> what is it ?

MapStruct代码生成器是基于约定优于配置，极大的简化了Java bean类型之间的映射的实现。生成代码的方式使用了普通方法调用，因此具有快速、类型安全和容易理解的特点。

> Why?

应用程序的多个层之间，通常需要不同的对象模型之间进行映射(例如do和dto直接映射)。编写这种映射代码是非常乏味且容易出错的任务。MapStruct旨在通过自动化尽可能简化这项工作。

> How?

MapStruct是注解处理器插入Java编译器,可用于命令行构建(Maven, Gradle等等)以及你喜欢的IDE中。MapStruct使用合理的默认值,但这些都来自于你的配置或指定的代码实现。

### 2分钟快速入门

这种工具没有太多的花样，官方文档已经很详细了，那我们就手把手学习官网吧，[mapstruct官网](https://mapstruct.org/)

> 基于maven安装

MapStruct是一个基于[JSR 269](http://www.jcp.org/en/jsr/detail?id=269) 规范的Java注解处理器，因此可以使用命令行构建或使用IDE构建，那我们就用最熟悉的IDEA来玩吧！

我只熟悉maven >.<，所以这里使用maven安装；grade, ant等其他安装方式参考官网哈

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

> MapStruct的插件下载

MapStruct贴心的提供了IDE的插件，用于快速生成bean的映射代码，可以直接在idea插件中心下载，也可使用以下方式下载后手动安装

MapStruct官网下载：[MapStruct IntelliJ IDEA plugin](https://mapstruct.org/news/2017-09-19-announcing-mapstruct-idea/)

jetbrains插件仓库下载：[MapStruct support for IntelliJ IDEA](https://plugins.jetbrains.com/plugin/10036-mapstruct-support)

> 第一个案例

待映射的两个bean （car / carDto）

```java
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;

    public Car(String make, int numberOfSeats, CarType type) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
        this.type = type;
    }
    // getter、setter方法省略...
}

public class CarDto {

    private String make;
    private int seatCount;
    private String type;
}

public enum CarType {
    SEDAN;
}
```

创建一个mapper

```java
@Mapper
public interface CarMapper {

    public static final CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);
}
```

@Mapper：指定了基于这个接口来进行bean的映射，mapstruct会基于这个接口生成实现类，内部实现映射代码

@Mapping：有时候进行两个bean之间的映射时，难免会出现字段名称不匹配，但是又希望两个字段互相映射，这个时候就需要手动指定映射关系；当调用映射方式carToCarDto时，要实现将numberOfSeats的值copy给seatCount。

由于我们这里没有使用DI框架(也就是没有使用spring)，想要获取mapper实例，则需要通过调用org.mapstruct.factory.Mappers类中的getMapper方法来实现。由于CarMapper只做bean的映射，因此没必要每次进行bean映射时，都去创建一个mapper实例。所以这里做成单例模式。

测试代码

```java
public class MapStructTest {

    @Test
    public void shouldMapCarToDto() {
        //given
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        //then
        Assert.assertNotNull(carDto);
        Assert.assertEquals("Morris", carDto.getMake());
        Assert.assertEquals(5, carDto.getSeatCount());
        Assert.assertEquals("SEDAN", carDto.getType());
    }
}
```

好了，到这里MapStruct就已经入门了，这样MapStruct就可以将car的字段内容映射到carDto里面了。

不过，我们好像发现一个问题，在spring的环境下mapper里面每次都创建个单例；正常来说，在spring环境下这种单例bean完全可以交由spring容器去管理，那么怎么搞呢？

MapStruct其实针对DI注入提供了三种方式：

cdi：使用@Inject注解注入

spring：使用@Autowired注解注入

jsr330：使用@Inject注解注入

因为我们企业开发基本都是依赖spring的，所以代码如下：

```java
@Mapper(componentModel = "spring")
public interface CarMapper {

    public static final CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);
}
```

这样配置的话，在其他地方想要使用CarMapper的时候，可以通过@Autowired注解来注入。

但是，(componentModel = "spring") 貌似每次都要写，这种每个mapper上面都去指定也挺烦的，而且都是重复的工作；这种我们能想到的问题，MapStruct怎么可能想不到，那么MapStruct是怎么做的呢？

MapStruct在maven中有个配置选项，用于进行全局配置，让MapStruct在编译的时候按照这个全局配置生成bean的映射代码；那么怎么配置呢？代码如下：

```java
    // pom.xml文件中
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

完美，到这里终于可以在项目代码中用起来了。

### 继续入门

为什么要继续入门呢？虽然我们已经算算是入门了，而且也可以在spring环境中用起来了；但是还不够，单纯的单层bean之间的映射，连企业中最基本的需求都无法满足，那么还差哪些呢？

- bean的嵌套组合（就是一个bean引用了另外一个bean，或另一个bean的集合）
- 集合的映射（集合中的bean引用了另一个bean的集合）

这两个如果都实现，应该就差不多了。因为如果bean定义的合理，bean之间的映射基本就以上这些情况。那接下来我们继续：

> bean里嵌套了一个普通bean

待映射的两个bean嵌套bean  Person -> Apple / PersonDto -> AppleDto

```java
public class Apple {

    private String color;

    public Apple(String color) {
        this.color = color;
    }

	// gettet、setter省略...

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                '}';
    }
}

public class Person {

    private String name;
    private Apple apple;

    // gettet、setter省略...

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", apple=" + apple +
                '}';
    }
}

public class AppleDto {

    private String color;

    public AppleDto(String color) {
        this.color = color;
    }

    // gettet、setter省略...

    @Override
    public String toString() {
        return "AppleDto{" +
                "color='" + color + '\'' +
                '}';
    }
}

public class PersonDto {

    private String name;
    private AppleDto appleDto;

    // gettet、setter省略...

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", appleDto=" + appleDto +
                '}';
    }
}
```

嵌套bean的mapper文件

```java
@Mapper
public interface AppleMapper {
	// AppleDto -> apple
    Apple toApple(AppleDto appleDto);
	// Apple -> AppleDto
    @InheritInverseConfiguration
    AppleDto fromApple(Apple apple);
}

@Mapper(uses = { AppleMapper.class })
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
	// PersonDto -> Person
    @Mapping(source = "appleDto", target = "apple")
    Person toPerson(PersonDto personDto);
	// Person -> PersonDto
    @Mapping(source = "apple", target = "appleDto")
    @InheritInverseConfiguration
    PersonDto fromPerson(Person person);
}
```

从以上代码可以看出，一个bean嵌套了另一个普通bean的关键点有3个地方：**

1. 嵌套的普通bean必须有自己的mapper文件，这样可以保证这个普通bean的属性都可以被映射，甚至可能出现字段不一样，可以进行手动映射
2. 在因为Apple被嵌套在Person里面，所以在PersonMapper映射文件定义时，需要代码：@Mapper(uses = {AppleMapper.class})指定嵌套关系；
3. 使用注解@InheritInverseConfiguration，方便bean的逆向转换。

> bean里面嵌套一个List集合

这里就不贴代码了，这种映射和上面的几乎一样；只要把Apple修改为List<Apple> appleList就可以了，代码链接我会附在文章末尾。

同样的，如何bean里面嵌套一个普通的List<String> strings, 映射方法和上面如出一辙，具体参考文末提供的代码连接

好了，到这里终于算是真的入门了，可以真正在项目种使用起来了，只不过肯定还会有一些坑，例如：

1. 有时候在bean映射的时候，可能想对值进行一些特殊处理后映射过去；例如日期格式转换 Date <---> String
2. 映射的时候想忽略一些字段，禁止映射过去
3. 映射的时候，如果没值，是否可以填充默认值

除了上面提到的几个坑外，可能还有其他的坑；以及是否还有更优雅的是否方式；更高阶的功能等等。































