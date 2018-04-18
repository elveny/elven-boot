/**
 * msxf.com Inc.
 * Copyright (c) 2018-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiusheng.wu
 * @Filename BaseController.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2018/4/18 20:47</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BaseController {

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return this.getClass().getSimpleName()+":home";
    }
}