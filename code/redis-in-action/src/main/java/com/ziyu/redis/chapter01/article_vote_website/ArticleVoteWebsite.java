package com.ziyu.redis.chapter01.article_vote_website;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ziyu
 */
public class ArticleVoteWebsite {

    private static final Integer ONE_WEEK_IN_SECONDS = 7 * 60 * 60 * 24;

    private static final Long VOTE_SCORE = 864L;

    private static final Long ARTICLES_PER_PAGE = 25L;

    private static final Jedis jedis;

    static {
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        jedis = jedisPool.getResource();
    }

    public static void main(String[] args) {

    }

    /**
     * 发布文章
     */
    public static void postArticle() {
        String user = "user:234487";

        String title = "文章标题1";
        String link = "http://localhost:8090/newArticle/1";

        // 生成一个新的文章ID,存储在hash列表中
        String articleId = String.valueOf(jedis.incr("article:"));
        Long time = System.currentTimeMillis();
        String article = "article:" + articleId;

        jedis.hmset(article, ImmutableMap.of("title", title,
                "link", link,
                "poster", user,
                "time", String.valueOf(time),
                "votes", String.valueOf(528)));

        jedis.zadd("time:", time, article);
        jedis.zadd("score:", time + VOTE_SCORE, article);

        // 发布文件用户添加到文章投票用户名单里面(只存一周，一周后不能评分)
        String voted = "voted:" + articleId;
        jedis.sadd(voted, user);
        jedis.expire(voted, ONE_WEEK_IN_SECONDS);

        System.out.println("articleId: " + articleId);
    }

    /**
     * 文章投票
     */
    public static void voteArticle() {
        String article = "article:1";
        String user = "user:115423";

        Long cutoff = System.currentTimeMillis() - ONE_WEEK_IN_SECONDS;
        if (jedis.zscore("time:", article) < cutoff) {
            return;
        }

        String articleId = article.split(":")[1];
        jedis.sadd("voted:" + articleId, user);
        jedis.zincrby("score:", VOTE_SCORE, article);
        jedis.hincrBy(article, "votes", 1);
    }

    /**
     * 获取评分最高的文章
     */
    public static void getArticles() {
        String order = "score:";
        long page = 1;
        long start = (page - 1) * ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE - 1;

        List<Map<String, String>> articles = new ArrayList<>();
        Set<String> ids = jedis.zrevrange(order, start, end);
        for (String id : ids) {
            Map<String, String> articleData = jedis.hgetAll(id);
            articleData.put("id", id);
            articles.add(articleData);
        }

        System.out.println(articles);
    }
}
