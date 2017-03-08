/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package tech.elven.boot.web.rest.test;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.elven.boot.plugins.data.jpa.entity.User;
import tech.elven.boot.plugins.data.jpa.repository.UserRepository;

import java.util.List;

/**
 * @Filename UserController.java
 *
 * @description
 *
 * @Version 1.0
 *
 * @author elven
 * @History
 *
 * <li>Author: elven</li>
 * <li>Date: 17-3-8 下午10:31</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@RestController
@RequestMapping("/boot.elven.tech/web/rest/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("findAll")
    @ResponseBody
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/save?name=elven&age=32.5
     * @param user
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/findOne?id=1
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    @ResponseBody
    public User findOne(long id){
        return userRepository.findOne(id);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/findOne/1
     * @param id
     * @return
     */
    @RequestMapping("findOne/{id}")
    @ResponseBody
    public User findOnePath(@PathVariable Long id){
        return userRepository.findOne(id);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/update?id=1&name=elven1&age=33
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public User update(User user){
        User oneUser = userRepository.findOne(user.getId());
        BeanUtils.copyProperties(user, oneUser);
        return userRepository.save(oneUser);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/count?id=1&name=elven2&age=33.0&version=8
     * @param user
     * @return
     */
    @RequestMapping("count")
    @ResponseBody
    public long count(User user){
        Example<User> example = Example.of(user);
        return userRepository.count(example);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/delete?id=1
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete(long id){
        userRepository.delete(id);
        return "SUCCESS";
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/exists/1
     * @param id
     * @return
     */
    @RequestMapping("exists/{id}")
    @ResponseBody
    public boolean exists(@PathVariable long id){
        return userRepository.exists(id);
    }

    /**
     * http://localhost:9999/boot.elven.tech/web/rest/user/exists?id=2&name=elven2&age=34.0&version=1
     * @param user
     * @return
     */
    @RequestMapping("exists")
    @ResponseBody
    public boolean exists(User user){
        Example<User> example = Example.of(user);
        return userRepository.exists(example);
    }
}