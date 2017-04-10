/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elven.boot.common.enums.ResultStatus;
import tech.elven.boot.plugins.webmagic.quickstart.baidu.xueshu.BaiduXueshuArticle;
import tech.elven.boot.plugins.webmagic.quickstart.baidu.xueshu.BaiduXueshuProcessor;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @Filename WebMagicController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-7 上午12:36</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/test/webmagic")
public class WebMagicController {
    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(WebMagicController.class);

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "home";
    }

    @RequestMapping("githubRepo")
    public String githubRepo(){
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).start();
        return ResultStatus.SUCCESS.code();
    }

    @RequestMapping("baiduXueshu")
    public String baiduXueshu(){
        Spider.create(new BaiduXueshuProcessor())
                .addUrl("http://xueshu.baidu.com/s?wd=spark&rsv_bp=0&tn=SE_baiduxueshu_c1gjeupa&rsv_spt=3&ie=utf-8&f=8&rsv_sug2=0&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D")
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .start();
        return ResultStatus.SUCCESS.code();
    }

    @RequestMapping("baiduXueshuArticle")
    public String baiduXueshuArticle(){
        OOSpider ooSpider = OOSpider.create(Site.me(), BaiduXueshuArticle.class);

        BaiduXueshuArticle article = ooSpider.get("http://xueshu.baidu.com/s?wd=paperuri%3A%28861e4d0498bd9b65a673e1d41f00bd34%29&filter=sc_long_sign&sc_ks_para=q%3DSpark%20Plasma%20Sintering%20of%20Alumina&sc_us=543466628458669838&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8");

        logger.info("{}", JSON.toJSONString(article));

        return JSON.toJSONString(article);
    }
}