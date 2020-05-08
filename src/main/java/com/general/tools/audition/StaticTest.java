package com.general.tools.audition;

public class StaticTest {
    static int cnt = 6;

    static {
        cnt += 9;
    }



    static {
        cnt /= 3;
    }

    {
        cnt += 4;
    }

    public StaticTest() {
        cnt += 5 ;
    }
    //先执行静态代码块 ，并且只会初始化一次
    //在执行普通代码块
    //在执行构造方法
    public static void main(String[] args) {
        System.out.println("cnt =" + cnt);
    }
}