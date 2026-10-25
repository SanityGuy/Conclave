package me.serenity.conclave.utils;

public class Logger {
    public static final String PREFIX = "[Conclave]";
    public static void log(String s) {
        System.out.println(PREFIX+" "+s);
    }

    // Easter egg
    public static void getLife() {
        while (true) {
            log("Wake up, shower, eat, work, home, sleep, Wake up");
            // Over and over again!
        }
    }
}
