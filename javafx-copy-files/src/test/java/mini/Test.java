package mini;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Stone
 * @since 2025-05-24
 */
public class Test {

    public static void main(String[] args) throws IOException {
        // 获取文件，移动到其他目录
        String inputFile = "C:\\Users\\Admin\\WPSDrive\\1455874631\\WPS企业云盘\\西洲\\团队文档\\西洲\\孟腾智能\\孟腾-2025-05-23\\请购单列表20250522-15点57分-300S-图纸(1)\\请购单列表20250522-15点57分-300S-图纸\\PR250521052NE252012-04-01水冷版大包装上料- 加工件 曹牧霓\\下单图纸\\NE252012-04-010204.pdf";
        String outputDir = "C:\\test\\output";

        File file = new File(inputFile);
        System.out.println(file.getAbsolutePath());

        /*FileUtil.copy(new File(inputFile),
                new File(outputDir + File.separator + "test222.pdf"), true);
        System.out.println("文件已移动到：" + outputDir);*/
    }
}
