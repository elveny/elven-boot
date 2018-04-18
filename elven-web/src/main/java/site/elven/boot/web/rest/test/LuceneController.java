/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.boot.common.enums.ResultStatus;
import site.elven.boot.plugins.lucene.LuceneUtils;
import site.elven.boot.plugins.poi.POIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Filename LuceneController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-23 下午11:05</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.site/web/rest/test/lucene")
public class LuceneController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LuceneController.class);

    private String excelFile = "classpath:site/elven/boot/plugins/poi/20170118.xlsx";
    private String indexDir = "out/site/elven/boot/plugins/lucene/index/bankInfo.index";

    /**
     * 刷新lucene index
     * @return ResultStatus
     */
    @RequestMapping("refresh")
    public String refresh() throws IOException {
        StopWatch stopWatch = new StopWatch("lucene.refresh");

        stopWatch.start("读取excel数据源");
        logger.info("开始读取excel数据源");
        long t1 = System.nanoTime();
        Map<String, List<String[]>> data = POIUtils.read(ResourceUtils.getFile(excelFile));
        long t2 = System.nanoTime();
        logger.info("读取20171018.xlsx花费时间："+(t2-t1)/1000000000.0f+"(s)");
        stopWatch.stop();

        stopWatch.start("组装lunece document，并创建index");
        logger.info("开始组装lunece document，并创建index");
        long t3 = System.nanoTime();

        // 读取excel数据，转换为lunece文档（document），为创建索引（index）做准备
        List<Document> documents = new ArrayList<Document>();

        for(String key : data.keySet()){

            if("SQL Results".equals(key)){
                List<String[]> list = data.get(key);
                for(String[] strs : list){

                    int i = 1;
                    String unitedBankNo = strs[i++];
                    String subBankName = strs[i++];
                    String clearBankNo = strs[i++];
                    String areaCode = strs[i++];
                    String areaName = strs[i++];
                    String parentAreaCode = strs[i++];

                    Document doc = new Document();
                    doc.add(new Field("unitedBankNo", unitedBankNo, TextField.TYPE_STORED));
                    doc.add(new Field("subBankName", subBankName, TextField.TYPE_STORED));
                    doc.add(new Field("clearBankNo", clearBankNo, TextField.TYPE_STORED));
                    doc.add(new Field("areaCode", areaCode, TextField.TYPE_STORED));
                    doc.add(new Field("areaName", areaName, TextField.TYPE_STORED));
                    doc.add(new Field("parentAreaCode", parentAreaCode, TextField.TYPE_STORED));

                    documents.add(doc);

                }

            }
            else {
                continue;
            }

        }

        // 初始化标准分析器（分词器）
        Analyzer analyzer = new StandardAnalyzer();

        // 创建lunece index目录
        Directory directory = FSDirectory.open(ResourceUtils.getFile(indexDir).toPath());

        // 创建lunece index
        LuceneUtils.createIndex(analyzer, directory, documents);

        directory.close();

        long t4 = System.nanoTime();

        logger.info("将excel数据转换为lunece document，并创建index，花费时间："+(t4-t3)/1000000000.0f+"(s)");
        stopWatch.stop();

        logger.info("{}", stopWatch.prettyPrint());
        return ResultStatus.SUCCESS.code();
    }

    /**
     * 搜索关键词
     * @param field
     * @param keyword
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("search/{field}/{keyword}")
    public List<Map<String, String>> search(@PathVariable String field, @PathVariable String keyword) throws IOException, ParseException {

        StopWatch stopWatch = new StopWatch("lucene.search");

        // 初始化标准分析器（分词器）
        Analyzer analyzer = new StandardAnalyzer();

        // 创建lunece index目录
        Directory directory = FSDirectory.open(ResourceUtils.getFile(indexDir).toPath());

        stopWatch.start("搜索");
        logger.info("开始搜索...");
        long t5 = System.nanoTime();
        List<Document> list = LuceneUtils.search(analyzer, directory, new String[]{field}, new String[]{keyword}, 1);
        directory.close();
        long t6 = System.nanoTime();
        logger.info("lunece搜索{}:{}花费时间："+(t6-t5)/1000000000.0f+"(s)", field, keyword);

        stopWatch.stop();

        logger.info("{}", stopWatch.prettyPrint());

        Assert.notEmpty(list);

        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        for(Document hitDoc : list){
            Map<String, String> result = new HashMap<String, String>();
            result.put("unitedBankNo", hitDoc.get("unitedBankNo"));
            result.put("subBankName", hitDoc.get("subBankName"));
            result.put("clearBankNo", hitDoc.get("clearBankNo"));
            result.put("areaCode", hitDoc.get("areaCode"));
            result.put("areaName", hitDoc.get("areaName"));
            result.put("parentAreaCode", hitDoc.get("parentAreaCode"));
            results.add(result);
        }

        return results;

    }
}