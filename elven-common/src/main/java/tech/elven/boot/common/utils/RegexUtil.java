/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Filename RegexUtil.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-4-7 下午11:39</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class RegexUtil {

    /**
     * 匹配验证：验证target是否符合regEx规则
     * @param regEx 验证规则
     * @param target 要验证的字符串
     * @return 验证结果：成功/失败
     */
    public static boolean matches(String regEx, String target){
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);

        // 字符串是否与正则表达式相匹配
        Matcher matcher = pattern.matcher(target);

        return matcher.matches();
    }

    /**
     * 字符查找：查找target中是否存在符合regEx规则的
     * @param regEx 查找规则
     * @param target 被查找的字符串
     * @return 查找结果：成功/失败
     */
    public static boolean find(String regEx, String target){
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);

        // 查找字符串中是否有匹配正则表达式的字符/字符串
        Matcher matcher = pattern.matcher(target);

        return matcher.find();
    }

    /**
     * 验证字符是否是中文：一个或多个汉字
     * @param target
     * @return
     */
    public static boolean checkChineseCharacter(String target){
        String regEx = "^[\\u0391-\\uFFE5]+$";
        return matches(regEx, target);
    }

    /**
     * 验证字符是否是中文：一个或多个汉字
     * @param target
     * @return
     */
    public static boolean findChineseCharacter(String target){
        String regEx = "[\\u0391-\\uFFE5]+";
        return find(regEx, target);
    }

    /**
     * 验证Email
     * @param target
     * @return
     */
    public static boolean checkEmail(String target){
//        String regEx = "^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$";
        String regEx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return matches(regEx, target);
    }
}