package win.pangniu.learn.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 超文 on 2016/10/20.
 */
// 1.4
/*@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
//@SpringApplicationConfiguration(classes = App.class) // 指定我们SpringBoot工程的Application启动类
//@ContextConfiguration(locations = {"classpath:spring-context-*.xml"})
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@SpringBootTest*/

// spring boot 1.5用法
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
}
