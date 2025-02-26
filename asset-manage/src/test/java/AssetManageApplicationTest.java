import com.wyd.AssetManageApplication;
import com.wyd.asset_manage.test.mybatis.ocp.OcpTest;
import com.wyd.asset_manage.test.mybatis.ocp.OcpTestDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: asset-manage
 * @description: 测试类
 * @author: Stone
 * @create: 2024-04-07 15:36
 **/
@SpringBootTest(classes = AssetManageApplication.class)
public class AssetManageApplicationTest {

    @Resource
    private OcpTestDao ocpTestDao;

    @Test
    public void testOpcTestDao() {
        OcpTest ocpTest = ocpTestDao.getOcpTest(1);
        System.out.println("获取到的内容为（id: " + ocpTest.getId() + "，test: " + ocpTest.getTest() + ")");
    }

}

