package com.ziyu.redis.commands_in_redis;

import com.ziyu.redis.util.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * list常用命令
 * rpush | lrange | lindex | lopo | del
 * @author ziyu
 */
public class ListCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        // 删除列表下的所有元素，防止影响后面的测试
        jedis.del("list-key");

        // 从右侧向列表推入新元素
        jedis.rpush("list-key", "item");
        jedis.rpush("list-key", "item2");
        jedis.rpush("list-key", "item");

        // 从左侧范围查找列表全部元素：0为起始索引位置，-1为结束索引位置(-1其实就代表列表的尾部最后一个元素)
        System.out.println(jedis.lrange("list-key", 0, -1));

        // 从列表中取出单个元素(1 - 为索引位置，从左侧0开始数)
        System.out.println(jedis.lindex("list-key", 1));

        // 从列表左侧弹出一个元素
        System.out.println(jedis.lpop("list-key"));

        // 查询弹出元素后的结果
        System.out.println(jedis.lrange("list-key", 0, -1));
    }
}
