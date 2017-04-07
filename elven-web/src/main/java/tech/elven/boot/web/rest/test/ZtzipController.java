/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elven.boot.common.enums.ResultStatus;
import tech.elven.boot.plugins.ztzip.ZtzipManager;

import java.io.File;
import java.io.IOException;

/**
 * @Filename ZtzipController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-8 上午12:40</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/ztzip")
public class ZtzipController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(ZtzipController.class);

    @Autowired
    private ZtzipManager ztzipManager;

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "home";
    }

    /**
     * 打包
     * @return
     * @throws IOException
     */
    @RequestMapping("pack")
    public ResultStatus pack() throws IOException {
        File sourceDir = new PathResource("logs").getFile();
        File targetZip = new PathResource("out/tech/elven/boot/plugins/ztzip/zips/log."+new DateTime().toString("yyyyMMddHHmmssSSS")+".zip").getFile();
        ztzipManager.packLogs(sourceDir, targetZip, "elven-boot");
        return ResultStatus.SUCCESS;
    }
}