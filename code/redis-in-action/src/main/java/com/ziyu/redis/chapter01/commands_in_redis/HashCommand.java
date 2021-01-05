package com.ziyu.redis.commands_in_redis;

import com.ziyu.redis.util.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * hash常用命令
 * hset | hget | hgetAll | hdel
 * @author ziyu
 */
public class HashCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        // 删除所有元素
        jedis.del("hash-key");

        // hash添加元素
        jedis.hset("hash-key", "sub-key1", "value1");
        jedis.hset("hash-key", "sub-key2", "value2");
        // 不能重复添加字段相同的元素
        jedis.hset("hash-key", "sub-key1", "value1");

        // 查询hash-key的所有元素
        System.out.println(jedis.hgetAll("hash-key"));

        // 重复删除键值为sub-key2的元素
        System.out.println(jedis.hdel("hash-key", "sub-key2"));
        System.out.println(jedis.hdel("hash-key", "sub-key2"));

        // 查询键值为sub-key的元素
        System.out.println(jedis.hget("hash-key", "sub-key1"));

        // 查询所有
        System.out.println(jedis.hgetAll("hash-key"));
    }
}
