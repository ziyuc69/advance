package com.ziyu.redis.chapter02;

import com.ziyu.redis.util.RedisUtils;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author ziyu
 */
public class LoginCookieCache {

    private static final Jedis jedis = RedisUtils.getJedis();

    public static void main(String[] args) throws Exception {
        jedis.select(1);

        new LoginCookieCache().run();
    }

    public void run() throws Exception {
        testLoginCookies();
        testShoppingCartCookies();
    }

    public void testLoginCookies() throws Exception {
        System.out.println("\n------ testLoginCookies ------");
        String token = UUID.randomUUID().toString();

        updateToken(token, "username", "itemX");
        System.out.println("we just logged-in/updated token: " + token);
        System.out.println("for user: 'username'");
        System.out.println();

        System.out.println("what username do we get when we look-up that token?");
        String r = checkToken(token);
        System.out.println(r);
        System.out.println();
        assert r != null;

        System.out.println("let's drop the maximum number of coolie to 0 to clean them out");
        System.out.println("we will start a thread to do the cleaning, while we stop is later");

        CleanSessionThread thread = new CleanSessionThread(jedis, 0);
        thread.start();
        Thread.sleep(1000);
        thread.quit();
        Thread.sleep(2000);
        if (thread.isAlive()) {
            throw new RuntimeException("The clean sessions thread is still alive?!?");
        }

        long s = jedis.hlen("login:");
        System.out.println("the current number of sessions still available is: " + s);
        assert s == 0;
    }

    public void testShoppingCartCookies() throws Exception {
        System.out.println("\n------ testShoppingCartCookies ------");
        String token = UUID.randomUUID().toString();

        System.out.println("we'll refresh our session......");
        updateToken(token, "username", "itemX");
        System.out.println("and add an item to the shopping cart");
        addToCart(token, "itemY", 3);

        Map<String, String> r = jedis.hgetAll("cart:" + token);
        r.forEach((k, v) -> {
            System.out.println(" " + k + ": " + v);
        });
        System.out.println();

        assert  r.size() >= 1;

        System.out.println("let's clean out our sessions and carts");
        CleanFullSessionsThread thread = new CleanFullSessionsThread(jedis, 0);
        thread.start();
        Thread.sleep(1000);
        thread.quit();
        Thread.sleep(2000);
        if (thread.isAlive()) {
            throw new RuntimeException("the clean sessions thread is still alive?!");
        }

        r = jedis.hgetAll("cart:" + token);
        System.out.println("our shopping cart now contains:");
        r.forEach((k, v) -> {
            System.out.println(" " + k + ": " + v);
        });
        assert r.size() == 0;
    }

    /**
     * 检查令牌，返回用户信息
     * @param token 令牌
     * @return
     */
    public String checkToken(String token) {
        return jedis.hget("login:", token);
    }

    /**
     * 更新令牌
     */
    public void updateToken(String token, String user, String item) {
        long timestamp = System.currentTimeMillis();
        // 维持令牌和用户的映射关系
        jedis.hset("login:", token, user);
        // 记录令牌最后一次出现的时间
        jedis.zadd("recent:", timestamp, token);
        // 如果用户浏览过商品，则记录；保留最近的25件商品
        if (StringUtils.isNotEmpty(item)) {
            jedis.zadd("viewed:" + token, timestamp, item);
            jedis.zremrangeByRank("viewed:" + token, 0, -26);
        }
    }

    /**
     * 添加购物车
     */
    public void addToCart(String token, String item, long count) {
        if (count <= 0) {
            // 移除指定的商品
            jedis.hdel("cart:" + token, item);
        } else {
            // 购物车添加商品
            jedis.hset("cart:" + token, item, String.valueOf(count));
        }
    }

    static class CleanSessionThread extends Thread {

        private Jedis jedis;
        private int limit;
        private boolean quit;

        public CleanSessionThread(Jedis jedis, int limit) {
            this.jedis = jedis;
            this.limit = limit;
            this.quit = false;
        }

        public void quit() {
            this.quit = true;
        }

        @Override
        public void run() {
            while (!quit) {
                // 找出目前已有令牌的数量
                long size = jedis.zcard("recent:");
                if (size <= limit) {
                    try {
                        sleep(1000);
                    }catch(InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }

                // 获取要删除的token
                // (size - LIMIT和100比较，意思是如果token比LIMIT超出了100个，
                // 一次只清理100个token，应该是防止一次清理过多占用cpu资源)
                long endIndex = Math.min(size - limit, 100);
                Set<String> tokenSet = jedis.zrange("recent:", 0, endIndex - 1);
                String[] tokens = tokenSet.toArray(new String[tokenSet.size()]);

                // 转换keys
                List<String> sessionKeys = new ArrayList<>();
                for (String token : tokens) {
                    sessionKeys.add("viewed:" + token);
                }

                jedis.del(sessionKeys.toArray(new String[sessionKeys.size()]));
                jedis.hdel("login:", tokens);
                jedis.zrem("recent:", tokens);
            }
        }
    }

    static class CleanFullSessionsThread extends Thread {
        private Jedis jedis;
        private int limit;
        private boolean quit;

        public CleanFullSessionsThread(Jedis jedis, int limit) {
            this.jedis = jedis;
            this.limit = limit;
            this.quit = false;
        }

        public void quit() {
            this.quit = true;
        }

        @Override
        public void run() {
            while (!quit) {
                long size = jedis.zcard("recent:");
                if (size <= limit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                long endIndex = Math.min(size - limit, 100);
                Set<String> sessionSet = jedis.zrange("recent:", 0, endIndex - 1);
                String[] sessions = sessionSet.toArray(new String[sessionSet.size()]);

                List<String> sessionKeys = new ArrayList<>();
                for (String session : sessions) {
                    sessionKeys.add("viewed:" + session);
                    sessionKeys.add("cart:" + session);
                }

                jedis.del(sessionKeys.toArray(new String[sessionKeys.size()]));
                jedis.hdel("login:", sessions);
                jedis.zrem("recent:", sessions);
            }
        }
    }
}
