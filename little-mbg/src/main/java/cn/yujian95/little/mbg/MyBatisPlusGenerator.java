package cn.yujian95.little.mbg;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author yujian95 clj9509@163.com
 * @date 10/3/2020
 */

public class MyBatisPlusGenerator {

    private static final String PROJECT_PATH_MBG = "mbg";
    private static final String PROJECT_PATH_ADMIN = "admin";
    private static final String PROJECT_PREFIX = "little-";

    private static final Props PROPS = new Props("generator.properties");

    public static void main(String[] args) {
        // 项目路径
        String path = System.getProperty("user.dir") + "\\" + PROJECT_PREFIX;
        String moduleName = scanner("模块名");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");

        // 生成共用 entity，mapper 代码。
        generate(path, moduleName, tableNames, PROJECT_PATH_MBG, "package.base", true);
        // 生成管理端 service，serviceImpl
//        generate(path, moduleName, tableNames, PROJECT_PATH_ADMIN, "package.admin");
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 生成 MBG 代码
     *
     * @param path        项目地址
     * @param moduleName  表模块
     * @param tableNames  表名称
     * @param project     项目标识
     * @param packagePath 子项目包地址
     */
    private static void generate(String path, String moduleName, String[] tableNames, String project, String packagePath, boolean isOnlyEntity) {

        // 子项目路径
        String projectPath = path + project;

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        autoGenerator.setDataSource(initDataSourceConfig());
        autoGenerator.setPackageInfo(initPackageConfig(moduleName, packagePath));

        // 仅在 mbg 子项目中生成 mapper
        if (PROJECT_PATH_MBG.equals(project) && !isOnlyEntity) {
            autoGenerator.setCfg(initInjectionConfig(projectPath, moduleName));
        }

        autoGenerator.setTemplate(initTemplateConfig(project, isOnlyEntity));
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "\\src\\main\\java");
        globalConfig.setAuthor("yujian95 yujian95_cn@163.com");
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        return globalConfig;
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(PROPS.getStr("dataSource.url"));
        dataSourceConfig.setDriverName(PROPS.getStr("dataSource.driverName"));
        dataSourceConfig.setUsername(PROPS.getStr("dataSource.username"));
        dataSourceConfig.setPassword(PROPS.getStr("dataSource.password"));
        return dataSourceConfig;
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig(String moduleName, String packagePath) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent(PROPS.getStr(packagePath));
        packageConfig.setEntity("entity");

        return packageConfig;
    }

    /**
     * 初始化模板配置
     */
    private static TemplateConfig initTemplateConfig(String moduleName, boolean isOnlyEntity) {
        TemplateConfig templateConfig = new TemplateConfig();

        // 只生成 ENTIY
        if (isOnlyEntity) {
            templateConfig.setController(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            templateConfig.setMapper(null);
            templateConfig.setXml(null);
            return templateConfig;
        }

        // Mbg项目，对 controller、service模板进行配置
        if (PROJECT_PATH_MBG.equals(moduleName)) {
            templateConfig.setController(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            templateConfig.setXml(null);
            return templateConfig;
        }

        // 其他子项目，除去配置生成
        templateConfig.setEntity(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);

        return templateConfig;
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();

        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setLogicDeleteFieldName("logic_delete");

        // 当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategyConfig.setInclude(tableNames);
        }

        return strategyConfig;
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath, String moduleName) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 可用于自定义属性
            }
        };
        // 模板引擎是Velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "\\src\\main\\resources\\mapper\\modules\\" + moduleName
                        + "\\" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }
}