/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.plugins.batch.tutorial.jobs.job1;

/**
 * @Filename Person.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-20 下午11:35</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class Person {
    private String lastName;
    private String firstName;

    public Person() {
    }

    public Person(String lastName, String firstName) {

        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}