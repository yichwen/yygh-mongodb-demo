package com.atguigu.mongo.demo.repository;

import com.atguigu.mongo.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
