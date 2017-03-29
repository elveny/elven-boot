/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Filename POIUtils.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 上午12:44</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class POIUtils {
    private static final Logger logger = LoggerFactory.getLogger(POIUtils.class);

    /**
     * excel2007以下的版本
     */
    public final static String EXCEL_TYPE_XLS = "xls";
    /**
     * excel2007及以上版本
     */
    public final static String EXCEL_TYPE_XLSX = "xlsx";
    /**
     * 文件格式错误提示
     */
    public final static String ERROR_FILE_TYPE = "文件格式不正确，请确保文件为xls或xlsx";

    /**
     * 读取excel文件内容：自动适配xls和xlsx文件，如果有新的文件格式，需要重新在下面解析
     * @param file excel文件
     * @return 整个文件的数据：以Map形式返回
     * 1、外层Map为整个文件，以sheet name为key，以行的集合为value
     * 2、List的数据为每一个sheet包含的数据，List的每一个元素就是excel的每一行
     * 3、String[]数据为每一行数据的数组
     * @throws IOException
     */
    public static Map<String, List<String[]>> parse(File file) throws IOException {
        Map<String, List<String[]>> result = new HashMap<String, List<String[]>>();

        String fileName = file.getName();

        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        // 校验文件后缀，如果没有适配的文件类型，则抛出异常
        if(EXCEL_TYPE_XLS.equalsIgnoreCase(suffix)){
            logger.info("开始读取excel(xls)文件...");
            // 读取excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));

            // 迭代sheet
            Iterator<Sheet> sheets = workbook.iterator();
            while (sheets.hasNext()){
                // 读取每一个sheet的数据
                HSSFSheet sheet = (HSSFSheet) sheets.next();

                // 读取sheet名称
                String sheetName = sheet.getSheetName();

                logger.info("开始读取sheet：{}", sheetName);

                // 迭代sheet的每一行
                Iterator<Row> rows = sheet.iterator();

                List<String[]> rowData = new ArrayList<String[]>();
                while (rows.hasNext()){
                    HSSFRow row = (HSSFRow) rows.next();

                    // 迭代每一行的每一列数据
                    Iterator<Cell> cells = row.iterator();
                    String[] cellData = new String[row.getLastCellNum()];
                    int cellNum = 0;
                    while (cells.hasNext()){
                        HSSFCell cell = (HSSFCell) cells.next();

                        cellData[cellNum++] = cell.toString();
                    }

                    rowData.add(cellData);
                }

                result.put(sheetName, rowData);
            }
        }
        else if(EXCEL_TYPE_XLSX.equalsIgnoreCase(suffix)){
            logger.info("开始读取excel(xlsx)文件...");
            // 读取excel文件
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

            // 迭代sheet
            Iterator<Sheet> sheets = workbook.iterator();
            while (sheets.hasNext()){
                // 读取每一个sheet的数据
                XSSFSheet sheet = (XSSFSheet) sheets.next();

                // 读取sheet名称
                String sheetName = sheet.getSheetName();

                logger.info("开始读取sheet：{}", sheetName);

                // 迭代sheet的每一行
                Iterator<Row> rows = sheet.iterator();

                List<String[]> rowData = new ArrayList<String[]>();
                while (rows.hasNext()){
                    XSSFRow row = (XSSFRow) rows.next();
                    logger.info("开始读取row：{}", row.getRowNum());

                    // 迭代每一行的每一列数据
                    Iterator<Cell> cells = row.iterator();
                    String[] cellData = new String[row.getLastCellNum()];
                    int cellNum = 0;
                    while (cells.hasNext()){
                        XSSFCell cell = (XSSFCell) cells.next();

                        cellData[cellNum++] = cell.toString();
                    }

                    rowData.add(cellData);
                }

                result.put(sheetName, rowData);
            }
        }
        else {
            throw new IOException(ERROR_FILE_TYPE);
        }

        return result;
    }
}