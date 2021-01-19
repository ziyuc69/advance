package com.ziyu.redis.chapter01.commands_in_redis;

import com.ziyu.redis.util.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * set常用命令
 * sadd | smembers | sismember | srem | del
 * @author ziyu
 */
public class SetCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        // 删除集合set-key的数据，防止影响后面测试
        jedis.del("set-key");

        // 给集合添加元素
        jedis.sadd("set-key", "item");
        jedis.sadd("set-key", "item1");
        jedis.sadd("set-key", "item2");
        jedis.sadd("set-key", "item3");
        // 重复添加元素测试(集合不允许元素重复)
        jedis.sadd("set-key", "item");

        // 查看集合中添加的所有元素
        System.out.println(jedis.smembers("set-key"));

        // 查询一个元素是否存在集合中
        System.out.println(jedis.sismember("set-key", "item4"));
        System.out.println(jedis.sismember("set-key", "item"));

        // 删除集合中的元素，返回元素在集合中的索引位置
        System.out.println(jedis.srem("set-key", "item1"));

        System.out.println(jedis.smembers("set-key"));
    }
}
