package com.cloud.photo.trans;

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
        String password = "qaz147896325";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("whh") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir("D:/Cloud-Photo/Cloud-Photo-3121003450/cloud-photo-trans" + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.cloud.photo") // 设置父包名
                            .moduleName("trans") // 设置父包模块名
                            .entity("entity") //设置entity包名
                            //  .other("model.dto") // 设置dto包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:/Cloud-Photo/Cloud-Photo-3121003450/cloud-photo-trans"
                                    + "/src/main/java/com/cloud/photo/trans/mapper")); // 设置mapperXml生成路径
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_user_file","tb_file_md5","tb_storage_object") // 设置需要生成的表名
                            .addTablePrefix("tb_"); // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
