package com.example.demo.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * @ClassName DyCode.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年09月14日 09:01:00
 */
public class DyCode {
    /**
     * 父类字段
     */
    private static final String SUPER_ENTITY_ID = "id";
    private static final String SUPER_ENTITY_CREATE_TIME = "create_time";
    private static final String SUPER_ENTITY_EDIT_TIME = "update_time";
    private static final String SUPER_ENTITY_DELETED = "deleted";
    private static final String[] SUPER_ENTITY_COLUMNS =
            {SUPER_ENTITY_ID, SUPER_ENTITY_CREATE_TIME, SUPER_ENTITY_EDIT_TIME, SUPER_ENTITY_DELETED};
    /**
     * 父类配置
     */
    private static final String PACKAGE_PARENT = "com.hxf.demo";

    private static final String SUPER_ENTITY = PACKAGE_PARENT + ".demo.codegen.base.BaseEntity";
    private static final String SUPER_CONTROLLER = PACKAGE_PARENT + ".demo.codegen.base.BaseController";
    private static final String SUPER_SERVICE = PACKAGE_PARENT + ".demo.codegen.base.BaseService";
    private static final String SUPER_SERVICE_IMPL = PACKAGE_PARENT + ".demo.codegen.base.BaseServiceImpl";

    /**
     * 包名配置
     */
    private static final String PACKAGE_PARENT_CONTROLLER = PACKAGE_PARENT + ".{0}.web.controller";
    private static final String PACKAGE_PARENT_SERVICE = PACKAGE_PARENT + ".{0}.service";
    private static final String PACKAGE_PARENT_SERVICE_IMPL = PACKAGE_PARENT + ".{0}.service.impl";
    private static final String PACKAGE_PARENT_MAPPER = PACKAGE_PARENT + ".{0}.dao.mapper";
    private static final String PACKAGE_PARENT_MAPPER_XML = PACKAGE_PARENT + ".{0}.dao.mapper.xml";
    private static final String PACKAGE_PARENT_ENTITY = PACKAGE_PARENT + ".{0}.dao.entity";


    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("vince");
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去Service的I 前缀
        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/smart-learning?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 3、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.ycspace.smartlearning.re");
        mpg.setPackageInfo(pc);

        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude("sl_device");// 设置要映射的表名
        strategy.setTablePrefix("sl_"); // 设置要映射的表的前缀
        strategy.setEntityLombokModel(true); // 自动 lombok
        strategy.setLogicDeleteFieldName("del_flag"); // 逻辑删除
        // 自动填充配置
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        strategy.setVersionFieldName("version");

        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true); // localhost:8080/hello_id_2

        // 自定义继承的Controller类全称，带包名
        strategy.setSuperControllerClass(SUPER_CONTROLLER);
        // 自定义继承的Service类全称，带包名
        strategy.setSuperServiceClass(SUPER_SERVICE);
        // 自定义继承的ServiceImpl类全称，带包名
        strategy.setSuperServiceImplClass(SUPER_SERVICE_IMPL);
        // 自定义基础的Entity类，公共字段
        strategy.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
        // 自定义继承的Mapper类全称，带包名
        strategy.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 逻辑删除属性名称
        strategy.setLogicDeleteFieldName(SUPER_ENTITY_DELETED);


        mpg.setStrategy(strategy);

        mpg.execute();

    }


}
