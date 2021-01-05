package com.ziyu.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ziyu
 */
public class RedisUtils {

    private static final Jedis jedis;

    static {
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        jedis = jedisPool.getResource();
    }

    public static Jedis getJedis() {
        return jedis;
    }
}
