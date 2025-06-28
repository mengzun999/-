package com.utils;

public class PatternCheck {
    private static final String PHONE_PATTERN = "^1[3456789]\\d{9}$";
    private static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{2,4}$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{4,10}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{6,16}$";
    private static final String AGE_PATTERN = "^\\d{1,2}$";
    public static boolean isPhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }
    public static boolean isChinese(String chinese) {
        return chinese.matches(CHINESE_PATTERN);
    }
    public static boolean isUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }
    public static boolean isPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
    public static boolean isAge(String age) {
        return age.matches(AGE_PATTERN);
    }
}
