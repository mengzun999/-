package com.service.impl;

import com.dao.AdminDao;
import com.dao.impl.AdminDaoImpl;
import com.service.AdminService;
import com.wang.supermarket.model.User;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    /**
     * 创建一个静态常量，用于调用Dao层
     */
    private static final AdminDao ADMIN_DAO = new AdminDaoImpl();
    @Override
    public List<User> findAll() {
        return ADMIN_DAO.findAll(AdminDao.FIND_USERS);
    }

    @Override
    public boolean addUser(User user) {
        return ADMIN_DAO.addUser(AdminDao.ADD_USER, user);
    }

    @Override
    public boolean hasUsername(String username) {
        return ADMIN_DAO.hasUsername(username);
    }

    @Override
    public boolean updateUser(Integer id) {
        return ADMIN_DAO.updateDel(id);
    }

    @Override
    public User findUserById(Integer id) {
        return ADMIN_DAO.findById(id);
    }

    @Override
    public boolean updateUserall(User user) {
        return ADMIN_DAO.updateUser(user);
    }

}
