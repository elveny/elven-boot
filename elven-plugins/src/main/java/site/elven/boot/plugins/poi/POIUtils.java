/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * @author qiusheng.wu
 * @Filename POIUtil.java
 * @description Apache poi 操作excel工具类
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/1/20 9:47</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class POIUtils {

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
     * 从InputStream中读取excel内容
     * 读取excel文件内容，根据不同的文件类型（xls和xlsx）获取不同的解析excel文件
     * @param is excel文件流
     * @param suffix 后缀名，只能是xls和xlsx
     * @return 整个文件的数据：以Map形式返回
     * 1、外层Map为整个文件，以sheet name为key，以行的集合为value
     * 2、List的数据为每一个sheet包含的数据，List的每一个元素就是excel的每一行
     * 3、String[]数据为每一行数据的数组
     * @throws IOException
     */
    public static Map<String, List<String[]>> read(InputStream is, String suffix) throws IOException {

        Map<String, List<String[]>> result = new HashMap<>(16);

        // 校验文件后缀，如果没有适配的文件类型，则抛出异常
        if(EXCEL_TYPE_XLS.equalsIgnoreCase(suffix)){
            // 读取excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(is);

            // 迭代sheet
            Iterator<Sheet> sheets = workbook.iterator();
            while (sheets.hasNext()){
                // 读取每一个sheet的数据
                HSSFSheet sheet = (HSSFSheet) sheets.next();

                // 读取sheet名称
                String sheetName = sheet.getSheetName();

                // 迭代sheet的每一行
                Iterator<Row> rows = sheet.iterator();

                List<String[]> rowData = new ArrayList<>();
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
            // 读取excel文件
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            // 迭代sheet
            Iterator<Sheet> sheets = workbook.iterator();
            while (sheets.hasNext()){
                // 读取每一个sheet的数据
                XSSFSheet sheet = (XSSFSheet) sheets.next();

                // 读取sheet名称
                String sheetName = sheet.getSheetName();

                // 迭代sheet的每一行
                Iterator<Row> rows = sheet.iterator();

                List<String[]> rowData = new ArrayList<>();
                while (rows.hasNext()){
                    XSSFRow row = (XSSFRow) rows.next();

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

    /**
     * 从byte[]中读取excel内容
     * @param data
     * @param suffix 后缀
     * @return
     * @throws IOException
     */
    public static Map<String, List<String[]>> read(byte[] data, String suffix) throws IOException {

        InputStream is = new ByteArrayInputStream(data);

        return read(is, suffix);
    }

    /**
     * 从文件中读取excel内容
     * @param file excel文件
     * @return
     * @throws IOException
     */
    public static Map<String, List<String[]>> read(File file) throws IOException {
        String fileName = file.getName();

        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        return read(new FileInputStream(file), suffix);
    }

    /**
     * 从文件路径中读取excel内容
     * @param filePath excel文件路径
     * @return
     * @throws IOException
     */
    public static Map<String, List<String[]>> read(String filePath) throws IOException {
        File file = new File(filePath);

        String fileName = file.getName();

        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        return read(new FileInputStream(file), suffix);
    }

    public static void write(Map<String, List<Object[]>> sheetMap, String type, OutputStream outputStream) throws IOException {

        Workbook workbook = null;

        if(EXCEL_TYPE_XLS.equalsIgnoreCase(type)){
            workbook = new HSSFWorkbook();
        }
        else if(EXCEL_TYPE_XLSX.equalsIgnoreCase(type)){
            workbook = new XSSFWorkbook();
        }
        else {
            throw new IOException(ERROR_FILE_TYPE);
        }

        if(sheetMap != null && !sheetMap.isEmpty()){
            for(String sheetName : sheetMap.keySet()){
                Sheet sheet = workbook.createSheet(sheetName);

                List<Object[]> rowList = sheetMap.get(sheetName);
                if(rowList != null && !rowList.isEmpty()){
                    int rowNum = 0;
                    for(Object[] cellArr : rowList){
                        Row row = sheet.createRow(rowNum++);

                        if(cellArr != null && cellArr.length > 0){
                            int cellNum = 0;
                            for (Object cellData : cellArr){
                                Cell cell = row.createCell(cellNum++);

                                if(cellData instanceof Double){
                                    cell.setCellValue((Double)cellData);
                                }
                                else if(cellData instanceof Date){
                                    cell.setCellValue((Date)cellData);
                                }
                                else if(cellData instanceof Calendar){
                                    cell.setCellValue((Calendar)cellData);
                                }
                                else if(cellData instanceof RichTextString){
                                    cell.setCellValue((RichTextString)cellData);
                                }
                                else if(cellData instanceof String){
                                    cell.setCellValue((String)cellData);
                                }
                                else{
                                    cell.setCellValue(String.valueOf(cellData));
                                }

                            }
                        }
                    }
                }

            }
        }

        workbook.write(outputStream);
    }

    public static void write(Map<String, List<Object[]>> sheetMap, String type, File file) throws IOException {
        write(sheetMap, type, new FileOutputStream(file));
    }

    public static void write(Map<String, List<Object[]>> sheetMap, String type, String filePath) throws IOException {
        write(sheetMap, type, new FileOutputStream(new File(filePath)));
    }
}