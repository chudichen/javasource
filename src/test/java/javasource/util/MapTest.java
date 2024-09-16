package javasource.util;

import com.study.javasource.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link HashMap}测试类
 *
 * @author Michael.Chu
 * @date 2019-04-18 20:47
 */
public class MapTest {

    /**
     * 测试创建
     */
    @Test
    public void createTest() {
        HashMap hashMap = new HashMap(1, 0.75F);
        Assert.assertNotNull(hashMap);
    }
}
