package com.cloud.photo.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Md5Util {

    public static void main(String[] args) {
        String filePath = "C:\\D\\培训\\测试图片\\test3.jpg";
        String md5 = getFileMd5(filePath);
        System.out.println(md5);
        System.out.println(new File(filePath).length());
    }

    public static String getFileMd5(String filePath){
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
