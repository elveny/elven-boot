/**
 * elven.tech Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.elven.boot.web.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.boot.plugins.data.jpa.repository.UserRepository;
import site.elven.boot.plugins.data.jpa.entity.User;

import java.util.List;

/**
 * user数据库测试Controller
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
@RequestMapping("/boot.elven.site/web/rest/test/user")
public class UserController {

    /** 日志记录器 **/
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /** user数据库仓储 **/
    @Autowired
    private UserRepository userRepository;

    /**
     * home
     * @return
     */
    @RequestMapping()
    public String home(){
        return "user:home";
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/findAll
     * @return
     */
    @RequestMapping("findAll")
    public List<User> findAll() {
        StopWatch stopWatch = new StopWatch("user");
        stopWatch.start("user.findAll");
        List<User> users = userRepository.findAll();
        stopWatch.stop();

        stopWatch.start("user.null");
        stopWatch.stop();
        logger.info("user \r\n{}",stopWatch.prettyPrint());
        return users;
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/save?name=elven&age=32.5
     * @param user
     * @return
     */
    @RequestMapping("save")
    public User save(User user){
        /**
         * spring.jpa.open_in_view=false会导致@Version乐观锁的version不会改变。
         * spring.jpa.open_in_view会控制org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor拦截器
         */
        // 第一步保存
        userRepository.save(user);
        /**
         * 第一步保存之后：
         * spring.jpa.open_in_view=false ===> version=0, 数据库表中version值0
         * spring.jpa.open_in_view=true ===> version=0, 数据库表中version值0
         */

        // 第二步保存
        user.setName(user.getName()+"_1");
        userRepository.save(user);
        /**
         * 第二步保存之后：
         * spring.jpa.open_in_view=false ===> version=0, 数据库表中version值1
         * spring.jpa.open_in_view=true ===> version=1, 数据库表中version值0
         */

        // 第三步保存
        user.setName(user.getName()+"_2");
        userRepository.save(user);
        /**
         * 第三步保存之后：
         * spring.jpa.open_in_view=false ===> 由于version=0，数据库表中的version的值已经是1，所以【报错：乐观锁不匹配错误】
         * spring.jpa.open_in_view=true ===> version=2, 数据库表中version值2
         */
        return user;
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/findOne?id=1
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public User findOne(long id){
        return userRepository.findOne(id);
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/findOne/1
     * @param id
     * @return
     */
    @RequestMapping("findOne/{id}")
    public User findOnePath(@PathVariable Long id){
        return userRepository.findOne(id);
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/update?id=1&name=elven1&age=33
     * @param user
     * @return
     */
    @RequestMapping("update")
    public User update(User user){
        User oneUser = userRepository.findOne(user.getId());
        BeanUtils.copyProperties(user, oneUser);
        return userRepository.save(oneUser);
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/count?id=1&name=elven2&age=33.0&version=8
     * @param user
     * @return
     */
    @RequestMapping("count")
    public long count(User user){
        Example<User> example = Example.of(user);
        return userRepository.count(example);
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/delete?id=1
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(long id){
        userRepository.delete(id);
        return "SUCCESS";
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/exists/1
     * @param id
     * @return
     */
    @RequestMapping("exists/{id}")
    public boolean exists(@PathVariable long id){
        return userRepository.exists(id);
    }

    /**
     * http://localhost:9999/boot.elven.site/web/rest/test/user/exists?id=2&name=elven2&age=34.0&version=1
     * @param user
     * @return
     */
    @RequestMapping("exists")
    public boolean exists(User user){
        Example<User> example = Example.of(user);
        return userRepository.exists(example);
    }
}