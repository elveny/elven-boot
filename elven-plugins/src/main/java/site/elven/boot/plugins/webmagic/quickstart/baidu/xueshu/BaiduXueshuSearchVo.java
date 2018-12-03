/**
 * elven.site Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.quickstart.baidu.xueshu;

/**
 * @author qiusheng.wu
 * @Filename BaiduXueshuSearchVo.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/4/8 15:40</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BaiduXueshuSearchVo {
    /** 标题 **/
    private String title;
    /** 作者 **/
    private String authors;
    /** 概要 **/
    private String summary;
    /** 来源 **/
    private String sources;
    /** 学科 **/
    private String subject;
    /** 网址 **/
    private String url;
    /** 下载地址 **/
    private String downUrl;

    public BaiduXueshuSearchVo() {
    }

    public BaiduXueshuSearchVo(String title, String authors, String summary, String sources, String subject) {
        this.title = title;
        this.authors = authors;
        this.summary = summary;
        this.sources = sources;
        this.subject = subject;
    }

    public BaiduXueshuSearchVo(String title, String authors, String summary, String sources, String subject, String url) {
        this.title = title;
        this.authors = authors;
        this.summary = summary;
        this.sources = sources;
        this.subject = subject;
        this.url = url;
    }

    public BaiduXueshuSearchVo(String title, String authors, String summary, String sources, String subject, String url, String downUrl) {
        this.title = title;
        this.authors = authors;
        this.summary = summary;
        this.sources = sources;
        this.subject = subject;
        this.url = url;
        this.downUrl = downUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}