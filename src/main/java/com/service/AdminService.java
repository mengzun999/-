package com.service;

import com.wang.supermarket.model.User;
import java.util.List;

public interface AdminService {
    List<User> findAll();
    boolean addUser(User user);
    boolean hasUsername(String username);
    boolean updateUser(Integer id);
    User findUserById(Integer id);
    boolean updateUserall(User user);
}
