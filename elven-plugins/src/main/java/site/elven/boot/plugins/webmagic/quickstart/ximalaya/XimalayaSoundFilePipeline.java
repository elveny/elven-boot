/**
 * msxf.com Inc.
 * Copyright (c) 2018-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.quickstart.ximalaya;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author qiusheng.wu
 * @Filename XimalayaSoundFilePipeline.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2018/4/13 12:27</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class XimalayaSoundFilePipeline implements Pipeline {

    Logger logger = LoggerFactory.getLogger(XimalayaSoundFilePipeline.class);

    private String filePath;

    private RestTemplate restTemplate;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public XimalayaSoundFilePipeline() {
    }

    public XimalayaSoundFilePipeline(String filePath) {
        this.filePath = filePath;
    }

    public XimalayaSoundFilePipeline(String filePath, RestTemplate restTemplate) {
        this.filePath = filePath;
        this.restTemplate = restTemplate;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        String soundFilePath = null;
        byte[] bytes = null;
        try {
            Map<String, Object> map = resultItems.get("soundObjMap");
            if(!CollectionUtils.isEmpty(map)){

                String id = String.valueOf(map.get("id"));
                String title = String.valueOf(map.get("title"));
                String album_id = String.valueOf(map.get("album_id"));
                String album_title = String.valueOf(map.get("album_title"));
                String nickname = String.valueOf(map.get("nickname"));
                String play_path = String.valueOf(map.get("play_path"));

                // 获取后缀名
                String suffix = FilenameUtils.getExtension(play_path);

                // 下载音频文件
                if(ObjectUtils.isEmpty(restTemplate)){
                    restTemplate = new RestTemplate();
                }
                bytes = restTemplate.getForObject(play_path, byte[].class);

                String soundTitle = album_id+"_"+id+"_"+title.replaceAll("[\\\\/:*?\"<>|]", "-");

                soundFilePath = filePath+File.separator+nickname.replaceAll("[\\\\/:*?\"<>|]", "-")+File.separator+album_title.replaceAll("[\\\\/:*?\"<>|]", "-")+File.separator+soundTitle+"."+suffix;

                if(!StringUtils.isEmpty(soundFilePath) && !ObjectUtils.isEmpty(bytes)){
                    // 保存文件
                    FileUtils.writeByteArrayToFile(new File(soundFilePath), bytes);
                }

            }
        } catch (IOException e) {
            logger.error("下载音频文件{}异常：{}", soundFilePath, e.getMessage(), e);

            if(!StringUtils.isEmpty(soundFilePath) && !ObjectUtils.isEmpty(bytes)){
                // 保存文件
                try {
                    FileUtils.writeByteArrayToFile(new File(soundFilePath), bytes);
                } catch (IOException e1) {
                    logger.error("再次下载音频文件{}异常：{}", soundFilePath, e.getMessage(), e);
                }
            }
        }
    }
}