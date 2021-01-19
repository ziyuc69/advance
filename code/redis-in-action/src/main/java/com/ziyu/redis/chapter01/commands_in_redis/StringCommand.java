package com.ziyu.redis.chapter01.commands_in_redis;

import com.ziyu.redis.util.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * string常用命令
 * set | get | del
 * @author ziyu
 */
public class StringCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        jedis.set("k1", "v1");
        System.out.println(jedis.get("k1"));

        jedis.del("k1");
        System.out.println(jedis.get("k1"));
    }
}
