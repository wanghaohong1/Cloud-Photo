package com.cloud.photo.audit;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/szsh_cloud?setUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "123456";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("wfc") // 设置作者
                           // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir("C:/Users/24415/Desktop/cloud-photo-g/cloud-photo-audit" + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.cloud.photo") // 设置父包名
                            .moduleName("audit") // 设置父包模块名
                            .entity("entity") //设置entity包名
                          //  .other("model.dto") // 设置dto包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:/Users/24415/Desktop/cloud-photo-g/cloud-photo-audit"
                                    + "/src/main/java/com/cloud/photo/audit/mapper")); // 设置mapperXml生成路径
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                   // customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_file_audit") // 设置需要生成的表名
                            .addTablePrefix("tb_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }
}
