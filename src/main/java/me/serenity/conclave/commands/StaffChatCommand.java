package me.serenity.conclave.commands;

import me.serenity.conclave.utils.C3;
import me.serenity.conclave.utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {

    private final String PREFIX = Logger.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)){
            Logger.log("You must be a player to do this!");
            return true;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("conclave.staffchat"))) {
            p.sendMessage(C3.color("&c"+PREFIX+" you must be staff to access this command!"));
        }

        p.sendMessage(C3.color("&aHello! Staff chat is on!"));

        return true;
    }
}
