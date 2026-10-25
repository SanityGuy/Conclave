package me.serenity.conclave.commands;

import me.serenity.conclave.Conclave;
import me.serenity.conclave.utils.C3;
import me.serenity.conclave.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player p)){
            Logger.log("You must be a player to do this!");
            return true;
        }

        String PREFIX = Logger.PREFIX;
        if (!(p.hasPermission("conclave.staffchat"))) {
            p.sendMessage(C3.color("&c"+ PREFIX +" you must be staff to access this command!"));
            return true;
        }

        if (label.equalsIgnoreCase("staffchattoggle") || args.length == 0) {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Conclave.getPlugin(Conclave.class), "staffchat_toggle");
            Byte current = pdc.get(key, PersistentDataType.BYTE);
            byte newValue = (byte) ((current != null && current == 1) ? 0 : 1);
            pdc.set(key, PersistentDataType.BYTE, newValue);
            p.sendMessage(C3.color("&b" + PREFIX + " staff chat has been &b&l" + (newValue == 1 ? "enabled" : "disabled")));

        } else {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Conclave.getPlugin(Conclave.class), "staffchat_toggle");
            Byte state = pdc.get(key, PersistentDataType.BYTE);
            if (state == null || state != 1) {
                p.sendMessage(C3.color("&c" + PREFIX + " staff chat is disabled. Use /staffchattoggle to enable."));
                return true;
            }
            String message = String.join(" ", args);
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.hasPermission("conclave.staffchat") && !other.isOp()) continue;
                other.sendMessage(C3.color("&8[&b&lSC&8] &r" + p.getName() + " &b> &r" + message));
            }
        }

        return true;
    }
}
