package cn.yujian95.hospital.common.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis 逆向工程（entity，mapper，mapper.xml)代码生成器
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/18
 */

public class Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    public static void main(String[] args) throws Exception {

        // MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<>();

        // 读取我们的 MBG 配置文件
        InputStream is = Generator.class.getResourceAsStream("/config/MybatisGeneratorConfig.xml");

        Configuration config = new ConfigurationParser(warnings).parseConfiguration(is);

        is.close();

        // 当生成的代码重复时，覆盖原代码
        DefaultShellCallback callback = new DefaultShellCallback(true);

        // 创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        // 执行生成代码
        myBatisGenerator.generate(null);

        // 输出警告信息
        for (String warning : warnings) {
            LOGGER.warn(warning);
        }
    }
}
