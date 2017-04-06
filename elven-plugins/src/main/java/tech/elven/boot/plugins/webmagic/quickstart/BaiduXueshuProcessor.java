/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.webmagic.quickstart;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Filename BaiduXueshuProcessor.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-7 上午12:39</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BaiduXueshuProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site
            .me()
            .setDomain("xueshu.baidu.com")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//div[@class='sc_content']/h3/a/text()").toString());
        page.putField("author", page.getHtml().xpath("//div[@class='sc_info']/span/a/all()").all());

    }

    @Override
    public Site getSite() {
        return site;
    }
}