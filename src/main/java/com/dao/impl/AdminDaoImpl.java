package com.dao.impl;
import com.dao.AdminDao;
import com.utils.BaseDao;
import com.wang.supermarket.model.User;
import com.utils.GenericDaoImpl;

public class AdminDaoImpl extends GenericDaoImpl<User, Integer> implements AdminDao {

    @Override
    public boolean addUser(String addUser, User user) {
        System.out.println("执行 addUser: " + user);
        return com.utils.BaseDao.executeCommand(AdminDao.ADD_USER, user.getUsername(), user.getPassword(),  user.getSex(), user.getMobile(), user.getAge(), user.getAddress(),user.getRole())==1;
    }

    @Override
    public boolean hasUsername(String username) {
        return com.utils.BaseDao.executeScalare(AdminDao.HAS_USERNAME,username)==1;
    }

    @Override
    public boolean updateDel(Integer id) {
        return BaseDao.executeCommand(AdminDao.UPDATE_DEL,id)==1;
    }

    @Override
    public User findById(Integer id) {
        return BaseDao.findByObject(AdminDao.FIND_USER_BY_ID,User.class,id);
    }

    @Override
    public boolean updateUser(User user) {
        System.out.println("执行 updateUserall: " + user);
        return BaseDao.executeCommand(AdminDao.UPDATE_ALL, user.getUsername(), user.getPassword(),  user.getSex(), user.getMobile(), user.getAge(),user.getRole(), user.getAddress(),user.getId())==1;
    }
}
