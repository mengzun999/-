package com.utils;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;




/**
 * 通用数据库操作类
 * @author 宋伟宁
 *
 * 2019年10月3日下午10:40:55
 */
public class BaseDao{

    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
    private static Connection connection;//连接
    private static PreparedStatement pst;//预编译
    private static ResultSet rs;//结果集
    private static CallableStatement cstmt;//执行存储过程对象
    private static Properties properties = new Properties();
    private static String URL, DRIVER, USER, PWD;
    private static int m;
    private static Logger logger= LoggerFactory.getLogger(BaseDao.class);
    /**
     * 静态块读取配置文件（获取数据库连接属性）
     * 获取数据库驱动
     */
    static {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(input);
            DRIVER = properties.getProperty("jdbc.driver");
            USER = properties.getProperty("jdbc.username");
            PWD = properties.getProperty("jdbc.password");
            URL = properties.getProperty("jdbc.url");
            Class.forName(DRIVER);
            logger.debug("driver: "+DRIVER+"\nurl: "+URL+"\nuser: "+USER+"\npwd: "+PWD+"\n");
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 通用的列表
     * @param sql
     * @param clazz
     * @param args
     * @return 泛型集合
     */
    public static <T> List<T> findAll(String sql,Class<T> clazz,Object...args){
        logger.debug("BaseDao..sql:"+sql);
        List<T> list=new ArrayList<T>(20);
        T t=null;
        try {
            pst=createPreparedStatement(sql, args);
            rs=pst.executeQuery();

            while(rs.next()){
                t=convert(clazz, rs);
                list.add(t);
            }
        } catch (SQLException e) {

        }finally{
            closeAll(rs,pst);
            closeConnection();
        }

        return list;
    }

    /**
     * 通用的分页
     * @param sql
     * @param clazz
     * @param currentPage
     * @param pagesize
     * @param args
     * @return
     */
    public static <T> com.utils.Pager<T> pageAll(String sql, Class<T> clazz, String currentPage, int pagesize, Object...args){
        String total_sql= sql.replace("*", "count(*)").replace("limit ?,?", "").replace("LIMIT ?,?", "");
        logger.debug("total_sql: "+total_sql);
        List<T> list=new ArrayList<T>(20);
        T t=null;
        com.utils.Pager<T> pager=null;
        try {
            int total=executeScalare(total_sql, args);
            pager=new com.utils.Pager<>(total, currentPage, pagesize);

            String sql_page=sql.replace("?,?", String.valueOf(pager.getOffset())+","+String.valueOf(pager.getPagesize()) );
            logger.debug("BaseDao..sql:"+sql_page);
            pst=createPreparedStatement(sql_page, args);

            rs=pst.executeQuery();

            while(rs.next()){
                t=convert(clazz, rs);
                list.add(t);
            }
            pager.setDatas(list);
        } catch (SQLException e) {
            logger.debug("BaseDao...分页查询 错误。。。。"+e.getMessage());
        }finally{
            closeAll(rs,pst);
            closeConnection();
        }

        return pager;
    }

    /**
     * 根据id或条件查询单个对象
     * @param sql
     * @param clazz
     * @param args
     * @return
     */
    public static <T> T findByObject(String sql,Class<T> clazz,Object...args){
        T t=null;
        try {
            pst=createPreparedStatement(sql, args);
            rs=pst.executeQuery();
            if(rs.next()){
                t=convert(clazz, rs);
            }
        } catch (Exception e) {
            logger.debug("BaseDao findById。。。。错误！"+e.getMessage());
        }finally{
            closeAll(rs,pst);
            closeConnection();
        }
        return t;
    }

    public static int executeScalare(String sql,Object...args){
        logger.debug(sql);

        try {
            pst= createPreparedStatement(sql,args);
            rs=pst.executeQuery();
            if(rs.next()){
                m=rs.getInt(1);
            }
        } catch (Exception e) {
            logger.debug("执行聚合函数错误。。。。");
        }finally{
            closeAll(rs,pst);
            closeConnection();
        }

        return m;
    }

    private static <T> T convert(Class<T> clazz,ResultSet rs) throws SQLException{
        ResultSetMetaData metaData=rs.getMetaData();
        int count=metaData.getColumnCount();
        T t=null;
        try {
            t=clazz.newInstance();
            for(int i=1;i<=count;i++){
                String str= metaData.getColumnLabel(i);
                Field field=clazz.getDeclaredField(str);
                field.setAccessible(true);
                field.set(t, rs.getObject(str));
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            logger.debug("类型转换，注入属性值错误："+e.getMessage());
        }
        return t;
    }


    /**
     * ThreadLocal模式的获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        try {
            connection= threadLocal.get();
            if(connection==null || connection.isClosed()){
                connection=DriverManager.getConnection(URL, USER,PWD);
                threadLocal.set(connection);
            }
        } catch (SQLException e) {
            logger.debug("创建connection 错误："+e.getMessage());
        }
        return connection;
    }

    /**
     * 释放数据库连接
     */
    public static void closeConnection(){
        connection = threadLocal.get();
        try {
            if(connection!=null && !connection.isClosed()){
                connection.close();
                threadLocal.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *释放所有数据库连接资源
     * @param closeables
     */
    public static void closeAll(AutoCloseable...closeables){
        for(AutoCloseable closeable : closeables){
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取预编译执行对象
     * @param sql
     * @param args
     * @return
     */
    public static PreparedStatement createPreparedStatement(String sql,Object...args){
        try {
            pst=getConnection().prepareStatement(sql);
            if(args!=null){
                for(int i=0;i<args.length;i++){
                    pst.setObject(i+1, args[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pst;
    }


    /**
     * 通用的增、删、改
     * @param sql
     * @param args
     * @return 受影响的行数
     */
    public static int executeCommand(String sql,Object...args){
        pst=createPreparedStatement(sql, args);
        try {
            m=pst.executeUpdate();
        } catch (SQLException e) {
            logger.debug("执行增、删、改错误："+e.getMessage());
        }finally{
            closeAll(pst);
            closeConnection();
        }
        return m;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                threadLocal.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                logger.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                threadLocal.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                logger.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                threadLocal.remove();
            }
        }
    }



    /**
     * 得到执行存储过程对象
     * @param pname
     * @param args
     * @return
     */
    private static CallableStatement createCallableStatement(String pname,Object...args){
        try {
            pname = pname.startsWith("{")? pname : "{call "+pname+"}";
            cstmt=getConnection().prepareCall(pname);
            if(args!=null){
                for(int i=0;i<args.length;i++){
                    cstmt.setObject(i+1, args[i]);
                }
            }
        } catch (SQLException e) {
            System.out.println("...........存储过程加载失败...........");
            e.printStackTrace();
        }
        return cstmt;
    }
    /**
     * 通用的存储过程实现增、删、改
     * @param pname
     * @param args
     * @return
     */
    public static int executeProcedure(String pname,Object...args){
        try {
            cstmt=createCallableStatement(pname, args);
            m=cstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("。。。。。。。。。执行增、删、改的存储过程错误。。。。。。。。");
            e.printStackTrace();
        }finally{
            closeAll(null, null, cstmt, getConnection());
        }
        return m;
    }

    /**
     * 使用存储过程查询所有
     * @param pname
     * @param clazz
     * @param args
     * @return
     */
    public static <T> List<T> procedureQuery(String pname,Class<T> clazz,Object...args){
        List<T> list = new ArrayList<T>(20);
        T t = null;
        try {
            cstmt=createCallableStatement(pname, args);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                t=convert(clazz, rs);
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("执行查询错误。。。。。");
            e.printStackTrace();
        }finally{
            closeAll(rs,cstmt);
            closeConnection();
        }
        return list;
    }

    /**
     * 根据条件查询单个对象的存储过程
     * @param pname
     * @param clazz
     * @param args
     * @return
     */
    public static <T> T procedureFindByObject(String pname,Class<T> clazz,Object...args){
        T t = null;
        try {
            cstmt=createCallableStatement(pname, args);
            rs = cstmt.executeQuery();
            if (rs.next()) {
                t =convert(clazz, rs);
            }
        } catch (Exception e) {
            System.out.println("执行查询错误。。。。。");
            e.printStackTrace();
        }finally{
            closeAll(rs,cstmt);
            closeConnection();
        }
        return t;
    }
}