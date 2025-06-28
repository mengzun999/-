package com.web;

import com.alibaba.fastjson.JSON;
import com.service.AdminService;
import com.service.impl.AdminServiceImpl;
import com.utils.PatternCheck;
import com.wang.supermarket.model.User;
import lombok.val;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(urlPatterns = {"/toUpdate","/chkAge","/updateUser","/userAdmin","/toadd","/addUser","/hasUsername","/chkPwd","/chkMobile"})
public class AdminServlet extends HttpServlet {
    private static final AdminService adminService = new AdminServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置编码
        req.setCharacterEncoding("utf-8");
        //设置响应编码
        resp.setCharacterEncoding("utf-8");
        String path = req.getServletPath();
        System.out.println(path);
        switch (path) {
            case "/toUpdate":
                toUpdate(req,resp);
                break;
            case "/chkPwd":
                chkPwd(req,resp);
                break;
            case "/hasUsername":
                hasUsername(req,resp);
                break;
            case "/addUser":
                addUser(req, resp);
                break;
            case "/toadd":
                toAdd(req, resp);
                break;
            case "/userAdmin":
                userList(req, resp);
                break;
            case "/updateUser":
                updateUser(req,resp);
                break;
            case "/chkAge":
                chkAge(req,resp);
                break;
            case "/chkMobile":
                chkMobile(req,resp);
                break;
        }
    }

    private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
    String idStr=req.getParameter("id");
        if (idStr == null || !idStr.matches("\\d+")) {
            resp.sendRedirect("userAdmin");
            return;
        }
        int id=Integer.parseInt(idStr);
        User user = adminService.findUserById(id);
        if (user == null){
            resp.sendRedirect("userAdmin");
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/admin/add.jsp").forward(req, resp);
    }

    //验证密码
    private void chkPwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        String pwd = req.getParameter("password");
        boolean b= PatternCheck.isPassword(pwd);
        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSON(b));
        out.close();
    }
    //查询所有用户
    protected void userList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //请求所有用户数据
        val list = adminService.findAll();
        req.setAttribute("users", list);
        req.getRequestDispatcher("/admin/userList.jsp").forward(req, resp);
    }
    //跳转到添加页面
    protected void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //跳转到添加页面
        req.getRequestDispatcher("/admin/add.jsp").forward(req, resp);
    }
    //验证用户名
    protected void hasUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("username");
        PrintWriter out = resp.getWriter();
        boolean b = adminService.hasUsername(name);
        out.print(JSON.toJSON(b));
        System.out.println("Received username: " + name); // 确认请求到达
        System.out.println("Check result: " + b); // 确认查询结果
        out.close();
    }
    //修改用户
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
        String sid=req.getParameter("id");
        int id=Integer.parseInt(sid);
        //返回结果
        boolean b = adminService.updateUser(id);
        //返回结果
        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSON(b));
        out.close();

    }
    //添加用户
    protected void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
            // 如果 id 存在，视为修改
            boolean isUpdate=user.getId()>0;
            if (isUpdate){
                adminService.updateUserall(user);
            }else{
                adminService.addUser(user);
            }
            resp.sendRedirect(req.getContextPath()+"/userAdmin");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    //验证手机号
    protected void chkMobile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //接收手机号
    String mobile=req.getParameter("mobile");
    //验证手机号
    boolean b = PatternCheck.isPhone(mobile);
    //返回结果
    PrintWriter out = resp.getWriter();
    out.print(JSON.toJSON(b));
    out.close();
}
    //验证年龄
    protected void chkAge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //接收年龄
    String age=req.getParameter("age");
    //验证年龄
    boolean b = PatternCheck.isAge(age);
    //返回结果
    PrintWriter out = resp.getWriter();
    out.print(JSON.toJSON(b));
    out.close();
}
}