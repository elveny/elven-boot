/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.ztzip;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.NameMapper;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

/**
 * @Filename ZtzipManager.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-8 上午12:24</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Component
public class ZtzipManager {
    /**
     * 打包log文件
     * !!!注意：ZipUtil在生成zip包的父亲路径必须存在，否则会报错“找到不路径”：
     * 例如，如果你要生成的zip文件路径为“/home/test/20170101/log.20170101010101000.zip”，那么父路径/home/test/20170101/必须是已存在的。
     * @param sourceDir log文件所在目录
     * @param targetZip 生成的zip包路径
     * @param prefix 打包以prefix开头的日志文件
     */
    public void packLogs(File sourceDir, File targetZip, final String prefix){
        if(StringUtils.isBlank(prefix)){
            // 生成压缩打包文件
            ZipUtil.pack(sourceDir, targetZip);
        }
        else {
            // 生成压缩打包文件
            ZipUtil.pack(sourceDir, targetZip, new NameMapper(){
                @Override
                public String map(String name) {
                    return name.startsWith(prefix) ? name : null;
                }
            });
        }
    }
}