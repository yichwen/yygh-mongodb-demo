package com.atguigu.mongo.demo;

import com.atguigu.mongo.demo.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
public class MongoDemoTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void createUser() {
        User user = new User();
        user.setName("tester");
        user.setAge(20);
        user.setEmail("abc@gmail.com");
        User inserted = mongoTemplate.insert(user);
        System.out.println(inserted);
    }

    @Test
    public void findAllUsers() {
        List<User> all = mongoTemplate.findAll(User.class);
        System.out.println(all);
    }

    @Test
    public void findUserById() {
        User user = mongoTemplate.findById("6080fe712de8ea79b7fa8da4", User.class);
        System.out.println(user);
    }

    @Test
    public void findUserByConditions() {
        Query query = new Query(Criteria.where("name").is("tester").and("age").is(20));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }

    @Test
    public void findUserUsingRegex() {
        String name = "est";
        String regex = String.format("%s%s%s", ".*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }

    @Test
    public void findUserByPage() {
        int pageNo = 1;
        int pageSize  = 3;
        // conditional
        String name = "est";
        String regex = String.format("%s%s%s", ".*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        // pagination
        long count = mongoTemplate.count(query, User.class);
        // records to skip
        long skip = (pageNo - 1) * pageSize;
        List<User> users = mongoTemplate.find(query.skip(skip).limit(pageSize), User.class);
        System.out.println(count);
        System.out.println(users);
    }

    @Test
    public void updateUser() {
        Query query = new Query(Criteria.where("id").is("6080fe712de8ea79b7fa8da4"));
        Update update = new Update();
        update.set("name", "tester");
        update.set("email", "abc@mail.com");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        System.out.println(updateResult);
    }

    @Test
    public void deleteUser() {
        Query query = new Query(Criteria.where("id").is("6080fe712de8ea79b7fa8da4"));
        DeleteResult deleteResult = mongoTemplate.remove(query, User.class);
        System.out.println(deleteResult);
    }

}

