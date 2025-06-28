package com.wang.supermarket;

import com.wang.supermarket.model.User;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
 public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     String username = request.getParameter("userName");
     String password = request.getParameter("passWord");
     System.out.println("账号"+username);
     System.out.println("密码"+password);
        User user = com.utils.BaseDao.findByObject("select * from t_user where username=? and password=? and is_del=0", User.class, username, password);
        if(user!=null){
         request.getSession().setAttribute("user",user);
         request.getRequestDispatcher("admin_index.jsp").forward(request,response);

     }else{
            request.setAttribute("errorMessage", "用户名或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request, response);
     }
 }
}