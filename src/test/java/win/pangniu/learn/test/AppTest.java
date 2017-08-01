package win.pangniu.learn.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class AppTest extends BaseTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        int[][] data = new int[10000][10000];
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                data[i][j] = i + j;
            }
        }
        System.err.println((System.currentTimeMillis() - startTime) + "ms");
    }

    @Test
    public void testRedisMap() {
        Object object = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().get("test");
                operations.delete("test");
                operations.opsForValue().set("test", "6");
                List<String> rs = operations.exec();
                System.out.println(" rs:" + rs.toString());
                return rs;
            }
        });
        List<Object> strings = (List<Object>) object;
        for (Object str : strings) {
            System.err.println(str.toString());
        }
    }

}
