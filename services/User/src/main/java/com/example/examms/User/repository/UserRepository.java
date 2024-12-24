package com.example.examms.User.repository;


import com.example.examms.User.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User , String> {

}
