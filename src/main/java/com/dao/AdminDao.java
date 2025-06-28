package com.dao;
import com.utils.*;
import com.wang.supermarket.model.User;

public interface AdminDao extends GenericDao<User, Number> {
    String FIND_USERS="select * from t_user";
    String ADD_USER="insert into t_user(username,password ,sex,mobile,age,address,role) values(?,?,?,?,?,?,?)";
    String HAS_USERNAME="SELECT count(id) from t_user where username=?";
    String UPDATE_DEL="UPDATE t_user set is_del= !is_del where id=?";
    String FIND_USER_BY_ID ="select * from t_user where id=?";
    String UPDATE_ALL="UPDATE t_user SET username=?,password=?,sex=?,mobile=?,age=?,role=?,address=? WHERE id=?;";
    boolean addUser(String addUser, User user);
    boolean hasUsername(String username);
    boolean updateDel(Integer id);
    User findById(Integer id);
    boolean updateUser(User user);
}
