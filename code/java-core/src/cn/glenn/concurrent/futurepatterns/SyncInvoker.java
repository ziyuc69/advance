package cn.glenn.concurrent.futurepatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-25 11:46
 *
 * Future           -> 代表的是未来的凭据
 * FutureTask       -> 将你的调用逻辑进行了隔离
 * FutureService    -> 桥接Future和FutureTask
 **/
public class SyncInvoker {
    public static void main(String[] args) throws InterruptedException {
        sync();
    }

    /** future.get()会同步阻塞 */
    public static void sync() throws InterruptedException {
        FutureService service = new FutureService();
        Future<String> future = service.submit(() -> {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "finish";
        });

        System.out.println("======================");
        System.out.println("do some ohter thing...");
        System.out.println("======================");
        System.out.println(future.get());
    }

    /** 异步调用，通过consumer函数式接口回调 */
    public static void async() {
        FutureService futureService = new FutureService();
        futureService.submit(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "finish";
        }, s -> {
            System.out.println("======================");
            System.out.println("do some ohter thing...");
            System.out.println("======================");
            System.out.println(s);
        });
    }
}
