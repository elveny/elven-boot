/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.webmagic.quickstart.baidu.xueshu;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

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

        List<BaiduXueshuSearchVo> searchResults = new ArrayList<BaiduXueshuSearchVo>();

        for (int i = 1; i <= 10; i++) {

            Selectable rootSelector = page.getHtml().xpath("//div[@id='"+i+"' and @class='result sc_default_result xpath-log']");
            if(rootSelector.match()){

                /** 标题 **/
//                String title = rootSelector.xpath("div[@class='sc_content']/h3[@class='t c_font']/a/allText()").get();
                Selectable titleSelector = rootSelector.xpath("div[@class='sc_content']/h3[@class='t c_font']/a");
                String title = titleSelector.xpath("allText()").get();
                /** 作者 **/
                String authors = rootSelector.xpath("div[@class='sc_content']/div[@class='sc_info']/a/text()").get();
                /** 概要 **/
                String summary = rootSelector.xpath("div[@class='sc_content']/div[@class='c_abstract']/text()").get();
                /** 来源 **/
                String sources = StringUtils.join(rootSelector.xpath("div[@class='sc_content']/div[@class='c_abstract']/div[@class='sc_allversion']/span[@class='v_item_span']/a[@class='v_source']/text()").all(), ",");
                /** 学科 **/
                String subject = StringUtils.join(rootSelector.xpath("div[@class='sc_ext']/div[@class='sc_subject']/a/text()").all(), ",");
                /** 网址 **/
                String url = rootSelector.xpath("div[@class='sc_content']/h3[@class='t c_font']/a/@href").get();

                searchResults.add(new BaiduXueshuSearchVo(title, authors, summary, sources, subject, url));
            }


        }
        page.putField("searchResults", JSON.toJSONString(searchResults));

    }

    @Override
    public Site getSite() {
        return site;
    }
}