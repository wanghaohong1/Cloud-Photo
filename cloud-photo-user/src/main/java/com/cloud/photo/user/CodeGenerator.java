package com.cloud.photo.user;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/photo?setUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "157158";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("linken") // 设置作者
                           // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir("D:/couldPhoto/cloud-photo-3121003445/cloud-photo-user" + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cloud.photo") // 设置父包名
                            .moduleName("user") // 设置父包模块名
                            .entity("entity") //设置entity包名
                          //  .other("model.dto") // 设置dto包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:/couldPhoto/cloud-photo-3121003445/cloud-photo-user"
                                    + "/src/main/java/cloud/photo/user/mapper")); // 设置mapperXml生成路径
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                   // customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_user") // 设置需要生成的表名
                            .addTablePrefix("tb_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }
}
