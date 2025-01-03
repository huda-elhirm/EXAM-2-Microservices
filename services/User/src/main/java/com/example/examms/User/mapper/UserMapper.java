package com.example.examms.User.mapper;

import com.example.examms.User.entity.User;
import com.example.examms.User.request.UserRequest;
import com.example.examms.User.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .id(request.id())
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .password(request.password())
                .build();
    }

    public UserResponse fromUser(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
