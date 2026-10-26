package me.serenity.conclave.utils;

import org.bukkit.Bukkit;

public class Logger {
    public static void log(String s){
        Bukkit.getLogger().info("[Conclave] "+s);
    }

    // Easter egg
    public static void getLife() {
        while (true) {
            log("Wake up, shower, eat, work, home, sleep, Wake up");
            // Over and over again!
        }
    }
}
