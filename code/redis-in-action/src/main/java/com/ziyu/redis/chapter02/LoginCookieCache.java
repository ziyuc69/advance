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

    public static void main(String[] args) {
        Random random = new Random();
        int goodSeq = random.nextInt(29) + 1;

        String token = UUID.randomUUID().toString();

        String user = checkToken(token);
        if (StringUtils.isEmpty(user)) {
            updateToken(token, "tom", "goods" + goodSeq);
        }

        cleanSessions();
    }

    /**
     * 检查令牌，返回用户信息
     * @param token 令牌
     * @return
     */
    private static String checkToken(String token) {
        return jedis.hget("login:", token);
    }

    /**
     * 更新令牌
     */
    private static void updateToken(String token, String user, String item) {
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
    private static void addToCart(String session, String item, long count) {
        if (count <= 0) {
            // 移除指定的商品
            jedis.hdel("cart:" + session, item);
        } else {
            // 购物车添加商品
            jedis.hset("cart:" + session, item, String.valueOf(count));
        }
    }

    /**
     * 后台扫描清理session和购物车
     */
    private static void cleanSessions() {
        Boolean quit = false;
        long LIMIT = 10000000;
        while (!quit) {
            // 找出目前已有令牌的数量
            long size = jedis.zcard("recent:");
            if (size <= LIMIT) {
                try {
                    Thread.sleep(1000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 获取要删除的token
            // (size - LIMIT和100比较，意思是如果token比LIMIT超出了100个，
            // 一次只清理100个token，应该是防止一次清理过多占用cpu资源)
            long endIndex = Math.min(size - LIMIT, 100);
            Set<String> tokens = jedis.zrange("recent:", 0, endIndex - 1);

            // 转换keys
            String[] viewedKeys = new String[tokens.size()];
            String[] sessionKeys = new String[tokens.size()];
            String[] cartKeys = new String[tokens.size()];

            List<String> tokenList = new ArrayList<>(tokens);
            for (int i = 0; i < tokenList.size(); i++) {
                cartKeys[i] = "cart:" + tokenList.get(i);
                viewedKeys[i] = "viewed:" + tokenList.get(i);
                sessionKeys[i] = tokenList.get(i);
            }

            // 移除旧的令牌
            jedis.del(viewedKeys);
            jedis.hdel("login:", sessionKeys);
            jedis.zrem("recent:", sessionKeys);
        }
    }
}
