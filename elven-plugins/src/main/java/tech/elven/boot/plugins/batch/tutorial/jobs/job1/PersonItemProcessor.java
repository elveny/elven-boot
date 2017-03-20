/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.batch.tutorial.jobs.job1;

import org.springframework.batch.item.ItemProcessor;
import tech.elven.boot.plugins.data.jpa.entity.User;

/**
 * @Filename PersonItemProcessor.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-20 下午11:38</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class PersonItemProcessor implements ItemProcessor<Person, User> {
    @Override
    public User process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        User user = new User(firstName+","+lastName, 20);

        return user;
    }
}