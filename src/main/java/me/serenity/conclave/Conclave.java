package me.serenity.conclave;

import me.serenity.conclave.commands.StaffChatCommand;
import me.serenity.conclave.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class Conclave extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.log("Plugin has been enabled!");

        getCommand("staffchat").setExecutor(new StaffChatCommand());
    }

    @Override
    public void onDisable() {
        Logger.log("Plugin has been disabled!");
    }
}
