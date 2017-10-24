/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.webmagic.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Filename SelectableOpType.java
 * @description
 * @Version 1.0
 * @author qiusheng.wu
 * @History
 * <br/>
 * <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/4/9 18:52</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public enum SelectableOpType {

    GET("get", "获取单个值"),
    ALL("all", "获取多个值"),
    ;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>SelectableOpType</code>枚举对象
     *
     * @param code
     * @param message
     */
    private SelectableOpType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return SelectableOpType
     */
    public static SelectableOpType getByCode(String code) {
        for (SelectableOpType _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<SelectableOpType>
     */
    public List<SelectableOpType> getAllEnum() {
        List<SelectableOpType> list = new ArrayList<SelectableOpType>();
        for (SelectableOpType _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (SelectableOpType _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }

}