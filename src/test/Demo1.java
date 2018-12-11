package test;

import org.junit.Test;

import java.text.MessageFormat;

public class Demo1 {
    @Test
    public void fun1() {
        String s = MessageFormat.format("{0}或{1}错误","用户名","密码" );
        System.out.println(s);
    }
}
