package cn.glenn.concurrent.guardedsuspensionpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-26 23:57
 **/
public class Request {

    private final String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
