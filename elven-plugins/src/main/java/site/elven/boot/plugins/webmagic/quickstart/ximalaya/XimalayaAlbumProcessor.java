/**
 * elven.site Inc.
 * Copyright (c) 2018-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.quickstart.ximalaya;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主播地址：http://www.ximalaya.com/zhubo/{主播id}/
 * 全部专辑：http://www.ximalaya.com/{主播id}/album/
 * 某个专辑：http://www.ximalaya.com/{主播id}/album/{专辑id}/
 * 音频地址：http://www.ximalaya.com/{主播id}/sound/{音频id}/
 * 音频json：http://www.ximalaya.com/tracks/{音频id}.json
 * @author qiusheng.wu
 * @Filename XimalayaAlbumProcessor.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2018/4/12 23:01</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class XimalayaAlbumProcessor implements PageProcessor {

    /**
     * 网站地址
     */
    private String domain = "http://www.ximalaya.com";

    /**
     * 音频json地址
     */
    private String soundJsonUrl = domain+"/tracks/%s.json";

    /**
     * 主播id
     */
    private String zhuboId;
    /**
     * 专辑id
     */
    private String albumId;
    /**
     * 声音id
     */
    private String soundId;

    public String getSoundJsonUrl() {
        return soundJsonUrl;
    }

    public void setSoundJsonUrl(String soundJsonUrl) {
        this.soundJsonUrl = soundJsonUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getZhuboId() {
        return zhuboId;
    }

    public void setZhuboId(String zhuboId) {
        this.zhuboId = zhuboId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getSoundId() {
        return soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site
            .me()
            .setDomain(domain)
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");



    @Override
    public void process(Page page) {

        Html html = page.getHtml();

        String htmlStr = html.get();

        // 校验html内容里面是否包含play_path，包含则表明是音频文件的json
        if(htmlStr.contains("play_path")){
            Map map = JSON.parseObject(page.getJson().get(), Map.class);
            page.putField("soundObjMap", map);
        }
        else{
            // 某个专辑页：将“所有音频”加入请求列表
            String sound_ids = html.xpath("//div[@class='personal_body']/@sound_ids").get();
            if(!StringUtils.isEmpty(sound_ids)){
                List<String> jsonUrls = new ArrayList<>();
                for(String soundId : sound_ids.split(",")){
                    jsonUrls.add(String.format(soundJsonUrl, soundId));
                }
                page.addTargetRequests(jsonUrls);
            }

            // 某个专辑下一页：将“下一页”加入请求列表
            String next = html.xpath("//div[@class='pagingBar_wrapper']/a[@class='pagingBar_page' and @rel='next']/@href").get();
            if(!StringUtils.isEmpty(next)){
                page.addTargetRequest(domain+next);
            }

            // 某个主播所有专辑：将“全部专辑”加入请求列表
            List<Selectable> allAlbums = html.xpath("//div[@class='body_list_wrap']/ul[@class='body_list album_list']/li[@class='item']").nodes();
            if(!CollectionUtils.isEmpty(allAlbums)){
                page.addTargetRequests(allAlbums.stream().map(node -> domain + "/" + zhuboId + "/" + "album" + "/" + node.xpath("/li[@class='item']/@album_id").get()).collect(Collectors.toList()));
            }

        }

    }

    @Override
    public Site getSite() {
        return site;
    }

}