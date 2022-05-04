package com.rds.mituan;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mituan", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("rds") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\javaIDE\\mituan\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.rds") // 设置父包名
                            .moduleName("mituan") ;// 设置父包模块名
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("employee") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")// 设置过滤表前缀
                            .entityBuilder().enableLombok(); //entry支持lombok
                })
                .strategyConfig(builder -> {
                    builder.controllerBuilder().enableRestStyle();//controller支持rest风格
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
