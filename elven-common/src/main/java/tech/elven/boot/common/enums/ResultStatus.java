/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Filename ResultStatus.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 * <br/>
 * <li>Author: elven</li>
 * <li>Date: 17-3-23 下午11:11</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public enum ResultStatus {

    SUCCESS("success", "成功"),
    FAIL("fail", "成功"),
    ;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>ResultStatus</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ResultStatus(String code, String message) {
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
     * @return ResultStatus
     */
    public static ResultStatus getByCode(String code) {
        for (ResultStatus _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ResultStatus>
     */
    public List<ResultStatus> getAllEnum() {
        List<ResultStatus> list = new ArrayList<ResultStatus>();
        for (ResultStatus _enum : values()) {
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
        for (ResultStatus _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }

}