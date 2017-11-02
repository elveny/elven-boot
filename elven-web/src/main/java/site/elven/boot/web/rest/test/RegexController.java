/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.boot.common.utils.RegexUtil;

/**
 * @Filename RegexController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-7 下午11:57</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.site/web/rest/test/regex")
public class RegexController {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(RegexController.class);

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "regex:home";
    }

    /**
     *
     * @param target
     * @return
     */
    @RequestMapping("checkChineseCharacter/{target}")
    public boolean checkChineseCharacter(@PathVariable String target) {
        return RegexUtil.checkChineseCharacter(target);
    }

    /**
     *
     * @param target
     * @return
     */
    @RequestMapping("checkEmail/{target}")
    public boolean checkEmail(@PathVariable String target) {
        return RegexUtil.checkEmail(target);
    }
}