/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.plugins.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.elven.boot.plugins.data.jpa.entity.User;

/**
 * @Filename UserRepository.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 * <br/>
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 下午10:28</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface UserRepository extends JpaRepository<User, Long> {
}