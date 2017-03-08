/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.data.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Filename User.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 下午10:18</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name="name")
    private String name;

    @Column(nullable = true)
    private double age;

    @Version
    @Column(nullable = false, name="version")
    private long version;

    public User() {
    }

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}