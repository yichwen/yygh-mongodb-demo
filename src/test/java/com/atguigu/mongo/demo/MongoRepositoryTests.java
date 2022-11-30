package com.atguigu.mongo.demo;

import com.atguigu.mongo.demo.entity.User;
import com.atguigu.mongo.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

@SpringBootTest
public class MongoRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User();
        user.setName("lucy");
        user.setAge(25);
        user.setEmail("lucy@mail.com");
        User saved = userRepository.save(user);
        System.out.println(saved);
    }

    @Test
    public void findAllUsers() {
        List<User> all = userRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void findUserById() {
        User user = userRepository.findById("60810c9335987640a6476c72").get();
        System.out.println(user);
    }

    @Test
    public void findUserByConditions() {
        User user = new User();
        user.setAge(25);
        user.setName("lucy");
        Example<User> example = Example.of(user);
        List<User> all = userRepository.findAll(example);
        System.out.println(all);
    }

    @Test
    public void findUserUsingProbe() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        User user = new User();
        user.setEmail("mail");
        Example<User> example = Example.of(user, matcher);
        List<User> all = userRepository.findAll(example);
        System.out.println(all);
    }

    @Test
    public void findUserByPage() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> page = userRepository.findAll(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getContent());
    }

    @Test
    public void updateUser() {
        User user = userRepository.findById("60810c9335987640a6476c72").get();
        user.setEmail("xxx@mail.com");
        User saved = userRepository.save(user);
        System.out.println(saved);
    }

    @Test
    public void deleteUser() {
        userRepository.deleteById("60810a7135397e17fa03b11d");
    }

}
