package com.ziyu.redis.chapter01.article_vote_website;

import com.google.common.collect.ImmutableMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ZParams;

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
        new ArticleVoteWebsite().run();
    }

    public void run() {
        // 发布文章
        String articleId = postArticle(
                "username", "A title", "http://baidu.com");
        System.out.println("we posted a new article with id: " + articleId);

        // 查看发布的文章
        System.out.println("Its HASH looks like:");
        Map<String, String> articleData = jedis.hgetAll("article:" + articleId);
        articleData.forEach((k, v) -> {
            System.out.println(" " + k + ": " + v);
        });

        System.out.println();

        // 投票
        articleVote("other_user", "article:" + articleId);
        String votes = jedis.hget("article:" + articleId, "votes");
        System.out.println("we voted for the article, it now has votes: " + votes);
        assert Integer.parseInt(votes) > 1;

        // 查找分值最大的文章
        System.out.println("the currently highest-scoring articles are:");
        List<Map<String, String>> articles = getArticles(1);
        printArticles(articles);
        assert articles.size() >= 1;

        addGroups(articleId, new String[]{"new-group"});
        System.out.println("we added the article to a new group, other articles include:");
        articles = getGroupArticles("new-group", 1);
        printArticles(articles);
        assert articles.size() >= 1;
    }

    /**
     * 发布文章
     */
    public String postArticle(String user, String title, String link) {
        // 生成一个新的文章ID,存储在hash列表中
        String articleId = String.valueOf(jedis.incr("article:"));

        // 发布文章用户添加到文章投票用户名单里面(只存一周，一周后不能评分)
        String voted = "voted:" + articleId;
        jedis.sadd(voted, user);
        jedis.expire(voted, ONE_WEEK_IN_SECONDS);

        long time = System.currentTimeMillis() / 1000;
        String article = "article:" + articleId;
        jedis.hmset(article, ImmutableMap.of("title", title,
                "link", link,
                "poster", user,
                "time", String.valueOf(time),
                "votes", "1"));

        jedis.zadd("time:", time, article);
        jedis.zadd("score:", time + VOTE_SCORE, article);

        return articleId;
    }

    /**
     * 文章投票
     */
    public void articleVote(String user, String article) {
        // 检查投票时间是否过期
        Long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
        if (jedis.zscore("time:", article) < cutoff) {
            return;
        }

        // 投票
        String articleId = article.substring(article.indexOf(":")  + 1);
        if (jedis.sadd("voted:" + articleId, user) == 1) {
            jedis.zincrby("score:", VOTE_SCORE, article);
            jedis.hincrBy(article, "votes", 1);
        }
    }

    /**
     * 按照评分排序，查找第一页文章
     */
    public List<Map<String, String>> getArticles(int page) {
        return getArticles(page, "score:");
    }

    public List<Map<String,String>> getGroupArticles(String group, int page) {
        return getGroupArticles(group, page, "score:");
    }

    public List<Map<String, String>> getArticles(int page, String order) {
        long start = (page - 1) * ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE - 1;

        Set<String> ids = jedis.zrevrange(order, start, end);
        List<Map<String, String>> articles = new ArrayList<>();
        for (String id : ids) {
            Map<String, String> articleData = jedis.hgetAll(id);
            articleData.put("id", id);
            articles.add(articleData);
        }

        return articles;
    }

    /**
     * 文章分组
     * @param articleId
     * @param toAdd
     */
    public void addGroups(String articleId, String[] toAdd) {
        String article = "article:" + articleId;
        for (String group : toAdd) {
            jedis.sadd("group:" + group, article);
        }
    }

    public List<Map<String, String>> getGroupArticles(String group, int page, String order) {
        String key = order + group;
        if (!jedis.exists(key)) {
            ZParams params = new ZParams().aggregate(ZParams.Aggregate.MAX);
            jedis.zinterstore(key, params, "group:" + group, order);
            jedis.expire(key, 60);
        }

        return getArticles(page, key);
    }


    private void printArticles(List<Map<String, String>> articles) {
        for (Map<String, String> article : articles) {
            System.out.println("id: " + article.get("id"));
            article.forEach((k, v) -> {
                System.out.println(" " + k + ": " + v);
            });
        }
    }
}
