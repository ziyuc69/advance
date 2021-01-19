package com.ziyu.redis.chapter01.commands_in_redis;

import com.ziyu.redis.util.RedisUtils;
import redis.clients.jedis.Jedis;

/**
 * zset常用命令
 * zadd | zrangeWithScores | zrangeByScoreWithScores | zrem
 * @author ziyu
 */
public class ZsetCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();

        // 删除元素，防止影响后面测试
        jedis.del("zset-key");

        // 添加元素
        jedis.zadd("zset-key", 728, "member1");
        jedis.zadd("zset-key", 982, "member0");
        jedis.zadd("zset-key", 982, "member0");

        // 获取有序集合元素时按照分值大小进行排序
        System.out.println(jedis.zrangeWithScores("zset-key", 0, -1));

        // 根据分值获取有序集合中的元素
        System.out.println(jedis.zrangeByScoreWithScores("zset-key", 0, 800));

        // 移除有序集合中的元素时，返回有序集合中的元素数量
        System.out.println(jedis.zrem("zset-key", "member1"));

        // 获取有序集合元素时按照分值大小进行排序
        System.out.println(jedis.zrangeWithScores("zset-key", 0, -1));
    }
}
