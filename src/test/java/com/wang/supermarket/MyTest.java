package com.wang.supermarket;

import com.utils.PatternCheck;
import org.junit.Test;

import java.util.regex.Pattern;

public class MyTest {
    @Test
    public void test()
    {
        System.out.println(com.utils.BaseDao.getConnection());
//        System.out.println(PatternCheck.iskAge("8"));
//        System.out.println(PatternCheck.isPhone("13452678901"));
//        System.out.println(PatternCheck.isUsername("wang"));
//        System.out.println(PatternCheck.isPassword("123456"));
//        System.out.println(PatternCheck.isChinese("王"));

        com.utils.BaseDao.closeConnection() ;
    }
}
