/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.manager;

import org.apache.commons.lang3.StringUtils;
import site.elven.boot.plugins.webmagic.enums.SelectableOpType;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author qiusheng.wu
 * @Filename WebMagicManager.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/4/9 18:42</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class WebMagicManager {

    public PageProcessor createXpathPageProcessor(String xpath, SelectableOpType opType){

        return new PageProcessor() {
            @Override
            public void process(Page page) {
                switch (opType){
                    case GET:
                        page.putField("results", page.getHtml().xpath(xpath).get());
                        break;
                    case ALL:
                        page.putField("results", page.getHtml().xpath(xpath).all());
                        break;

                        default:
                            page.putField("results", page.getHtml().xpath(xpath).get());
                }

            }

            @Override
            public Site getSite() {
                return Site.me();
            }
        };

    }

    public PageProcessor createLinksPageProcessor(String regex){
        return new PageProcessor() {
            @Override
            public void process(Page page) {
                if(StringUtils.isBlank(regex)){
                    page.putField("results", page.getHtml().links().all());
                }
                else {
                    page.putField("results", page.getHtml().links().regex(regex).all());
                }
            }

            @Override
            public Site getSite() {
                return Site.me();
            }
        };
    }

    public void spiderXpathTester(String url, String xpath, SelectableOpType opType){
        Spider.create(createXpathPageProcessor(xpath, opType))
                .addUrl(url)
                .addPipeline(new ConsolePipeline())
                .thread(5).run();
    }

    public void spiderLinksTester(String url, String regex){
        Spider.create(createLinksPageProcessor(regex))
                .addUrl(url)
                .addPipeline(new ConsolePipeline())
                .thread(5).run();
    }
}