package me.serenity.conclave.utils;

public class Logger {
    public static final String PREFIX = "[Conclave]";
    public static void log(String s) {
        System.out.println(PREFIX+" "+s);
    }
}
