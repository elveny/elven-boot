/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.rabbitmq.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Filename MqObjTypeEnum.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 * <br/>
 * <li>Author: elven</li>
 * <li>Date: 17-3-9 下午10:19</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public enum MqObjTypeEnum {

    QUEUE("queue", "队列"),
    EXCHANGE("exchange", "交换机"),
    ;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>MqObjTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private MqObjTypeEnum(String code, String message) {
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
     * @return MqObjTypeEnum
     */
    public static MqObjTypeEnum getByCode(String code) {
        for (MqObjTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<MqObjTypeEnum>
     */
    public List<MqObjTypeEnum> getAllEnum() {
        List<MqObjTypeEnum> list = new ArrayList<MqObjTypeEnum>();
        for (MqObjTypeEnum _enum : values()) {
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
        for (MqObjTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }

}