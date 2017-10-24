/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Filename LuceneUtils.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 上午12:43</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class LuceneUtils {
    /**
     * 创建索引
     * @param analyzer Lucene分析器Analyzer
     * @param directory 索引保存目录
     * @param documents Lucene文档集合
     * @throws IOException
     */
    public static void createIndex(Analyzer analyzer, Directory directory, List<Document> documents) throws IOException {

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        indexWriter.addDocuments(documents);

        indexWriter.close();
    }

    /**
     * 执行搜索
     * @param analyzer Lucene分析器Analyzer
     * @param directory 索引保存目录
     * @param fields 匹配字段
     * @param queries 搜索关键字
     * @param numHits top n
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static List<Document> search(Analyzer analyzer, Directory directory, String[] fields, String[] queries, int numHits) throws IOException, ParseException {
        List<Document> result = new ArrayList<Document>();

        // 打开索引目录
        DirectoryReader directoryReader = DirectoryReader.open(directory);

        // 构建索引搜索引擎
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        int fieldsNum = fields.length;
        BooleanClause.Occur[] flags = new BooleanClause.Occur[fieldsNum];
        for(int i=0; i<fieldsNum; i++){
            flags[i] = BooleanClause.Occur.MUST;
        }

        Query query = MultiFieldQueryParser.parse(queries, fields, flags, analyzer);

        // 搜索top n的数据
        ScoreDoc[] hits = indexSearcher.search(query, numHits).scoreDocs;

        //迭代结果
        for (ScoreDoc hit : hits) {
            Document hitDoc = indexSearcher.doc(hit.doc);
            result.add(hitDoc);
        }
        directoryReader.close();

        return result;
    }
}