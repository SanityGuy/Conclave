package me.serenity.conclave;

import me.serenity.conclave.commands.StaffChatCommand;
import me.serenity.conclave.listeners.StaffChatListener;
import me.serenity.conclave.utils.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Conclave extends JavaPlugin {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static Conclave instance;
    private NamespacedKey toggleKey;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        toggleKey = new NamespacedKey(this, "staffchat_toggle");
        getServer().getPluginManager().registerEvents(new StaffChatListener(), this);
        registerCommand("staffchat", new StaffChatCommand());
        registerCommand("staffchattoggle", new StaffChatCommand());

        Logger.log("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Logger.log("Plugin has been disabled");
    }

    public static Conclave get() {
        return instance;
    }

    public NamespacedKey toggleKey() {
        return toggleKey;
    }

    public static boolean enabled() {
        return get().getConfig().getBoolean("general.enabled", true);
    }

    public static boolean prefixEnabled() {
        return get().getConfig().getBoolean("general.prefix-enabled");
    }

    public static String prefix() {
        return get().getConfig().getString("general.prefix", "");
    }

    public static String chatFormat() {
        return get().getConfig().getString("chat.format", "<gray>[<blue>SC</blue></gray>] <reset>{player} <blue>»</blue> <reset>{message}");
    }

    public static boolean consoleLog() {
        return get().getConfig().getBoolean("chat.console-log", true);
    }

    public static int cooldownSeconds() {
        return get().getConfig().getInt("chat.cooldown-seconds", 3);
    }

    public static int maxLength() {
        return get().getConfig().getInt("chat.max-length", 128);
    }

    public static String permBase() {
        return get().getConfig().getString("permissions.base", "conclave.staffchat");
    }

    public static String permBypassCooldown() {
        return get().getConfig().getString("permissions.bypass-cooldown", "conclave.staffchat.bypass-cooldown");
    }

    public static Component msg(String key) {
        String raw = get().getConfig().getString("messages." + key, "<red>[Conclave] Missing: " + key);
        return MM.deserialize(raw);
    }

    public static Component prefixed(Component message) {
        if (!prefixEnabled()) return message;
        return MM.deserialize(prefix()).append(message);
    }

    public static Component msg(String key, String placeholder, String value) {
        String raw = get().getConfig().getString("messages." + key, "<red>[Conclave] Missing: " + key);
        raw = raw.replace(placeholder, value);
        return MM.deserialize(raw);
    }

    private void registerCommand(String cmd, CommandExecutor executor) {
        PluginCommand command = Objects.requireNonNull(
                getCommand(cmd), "Command not found in plugin.yml: " + cmd
        );
        command.setExecutor(executor);
    }
}