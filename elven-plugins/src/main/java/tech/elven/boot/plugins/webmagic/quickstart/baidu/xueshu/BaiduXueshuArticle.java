/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.webmagic.quickstart.baidu.xueshu;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * @Filename BaiduXueshuArticle.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-9 下午11:05</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@TargetUrl("http://xueshu.baidu.com/s?wd=paperuri%3A%28861e4d0498bd9b65a673e1d41f00bd34%29&filter=sc_long_sign&sc_ks_para=q%3DSpark%20Plasma%20Sintering%20of%20Alumina&sc_us=543466628458669838&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8")
public class BaiduXueshuArticle {
    /** 标题 **/
    @ExtractBy("//div[@id='dtl_l']/div[1]/h3/a/text()")
    private String title;
    /** 作者 **/
    @ExtractBy("//div[@class='c_content content_hidden']/div[@class='author_wr']/p[@class='author_text']/a/text()")
    private List<String> authors;
    /** 概要 **/
    @ExtractBy("//div[@class='c_content content_hidden']/div[@class='abstract_wr']/p[@class='abstract']/text()")
    private String summary;
    /** 来源 **/
    @ExtractBy("//div[@class='c_content content_hidden']/div[@class='publish_wr']/p[@class='publish_text']/allText()")
    private String publish;
    /** 学科 **/
    @ExtractBy("//div[@class='c_content content_hidden']/div[@class='ref_wr']/p[2]/a/text()")
    private int reference;

    public BaiduXueshuArticle() {
    }

    public BaiduXueshuArticle(String title, List<String> authors, String summary, String publish, int reference) {
        this.title = title;
        this.authors = authors;
        this.summary = summary;
        this.publish = publish;
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
}