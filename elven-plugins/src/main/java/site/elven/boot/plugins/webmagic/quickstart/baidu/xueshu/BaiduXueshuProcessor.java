/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.quickstart.baidu.xueshu;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
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

        List<BaiduXueshuSearchVo> searchResults = new ArrayList<>();
        List<Selectable> searchResultNodes = page.getHtml().xpath("//div[@class='result sc_default_result xpath-log']").nodes();


        for (Selectable node : searchResultNodes) {

            /** 标题 **/
            Selectable titleSelector = node.xpath("div[@class='sc_content']/h3[@class='t c_font']/a");
            String title = titleSelector.xpath("allText()").get();
            /** 作者 **/
            String authors = node.xpath("div[@class='sc_content']/div[@class='sc_info']/a/text()").get();
            /** 概要 **/
            String summary = node.xpath("div[@class='sc_content']/div[@class='c_abstract']/text()").get();
            /** 来源 **/
            String sources = StringUtils.join(node.xpath("div[@class='sc_content']/div[@class='c_abstract']/div[@class='sc_allversion']/span[@class='v_item_span']/a[@class='v_source']/text()").all(), ",");
            /** 学科 **/
            String subject = StringUtils.join(node.xpath("div[@class='sc_ext']/div[@class='sc_subject']/a/text()").all(), ",");
            /** 网址 **/
            String url = node.xpath("div[@class='sc_content']/h3[@class='t c_font']/a/@href").get();
            /** 下载地址 **/
            String downUrl = node.xpath("div[@class='sc_ext']/div[@class='sc_other']/a/@href").get();

            searchResults.add(new BaiduXueshuSearchVo(title, authors, summary, sources, subject, url, downUrl));

        }
        page.putField("searchResults", JSON.toJSONString(searchResults));

        // 把这些URL加到抓取列表中去
        List<String> urls = page.getHtml().links().regex("http://xueshu.baidu.com/s\\?wd=\\w+&pn=\\w+&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D&sc_hit=1").all();
        page.addTargetRequests(urls);
    }

    @Override
    public Site getSite() {
        return site;
    }
}