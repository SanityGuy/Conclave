package me.serenity.conclave.commands;

import me.serenity.conclave.Conclave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

    private static final MiniMessage MM = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!p.hasPermission("conclave.staffchat")) {
            p.sendMessage(MM.deserialize("<red>You must be staff to access this command!"));
            return true;
        }

        if (label.equalsIgnoreCase("staffchattoggle") || args.length == 0) {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Conclave.getPlugin(Conclave.class), "staffchat_toggle");
            Byte current = pdc.get(key, PersistentDataType.BYTE);
            byte newValue = (byte) ((current != null && current == 1) ? 0 : 1);
            pdc.set(key, PersistentDataType.BYTE, newValue);
            p.sendMessage(MM.deserialize("<blue>Staff chat has been <blue><bold>" + (newValue == 1 ? "enabled" : "disabled") + "</blue></bold>"));

        } else {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Conclave.getPlugin(Conclave.class), "staffchat_toggle");
            Byte state = pdc.get(key, PersistentDataType.BYTE);
            if (state == null || state != 1) {
                p.sendMessage(MM.deserialize("<red>Staff chat is disabled. Use /staffchattoggle to enable."));
                return true;
            }
            String message = String.join(" ", args);
            Component formatted = MM.deserialize("<gray>[<blue><bold>SC</blue></gray>] <reset>" + p.name() + " <blue>> <reset>")
                    .append(net.kyori.adventure.text.Component.text(message));
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.hasPermission("conclave.staffchat")) continue;
                other.sendMessage(formatted);
            }
        }

        return true;
    }
}