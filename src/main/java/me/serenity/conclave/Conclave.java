package me.serenity.conclave;

import me.serenity.conclave.commands.StaffChatCommand;
import me.serenity.conclave.utils.Logger;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Conclave extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand("staffchat", new StaffChatCommand());
        registerCommand("staffchattoggle", new StaffChatCommand());

        Logger.log("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        Logger.log("Plugin has been disabled!");
    }

    private void registerCommand(String cmd, CommandExecutor executor) {
        PluginCommand command = Objects.requireNonNull(
                getCommand(cmd),
                "Command not found in plugin.yml: " + cmd
        );
        command.setExecutor(executor);
    }
}