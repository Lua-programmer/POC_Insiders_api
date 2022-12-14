package io.github.luaprogrammer.poc.utils;


public class HashCode {
    public static String format(String s) {

        s = s.replace('0', '*');
        s = s.replace('1', '*');
        s = s.replace('2', '*');
        s = s.replace('3', '*');
        s = s.replace('4', '*');
        s = s.replace('5', '*');
        s = s.replace('6', '*');
        s = s.replace('7', '*');
        s = s.replace('8', '*');
        s = s.replace('9', '*');
        return s;
    }
}
