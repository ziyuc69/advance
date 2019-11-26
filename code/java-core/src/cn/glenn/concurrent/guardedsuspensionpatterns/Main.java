package cn.glenn.concurrent.guardedsuspensionpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-25 19:24
 **/
public class Main {
    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread(requestQueue, "Alice", 3141592L).start();
        new ServerThread(requestQueue, "Bobby", 6535897L).start();
    }
}
