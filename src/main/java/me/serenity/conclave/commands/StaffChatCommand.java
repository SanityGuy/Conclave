package me.serenity.conclave.commands;

import me.serenity.conclave.Conclave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffChatCommand implements CommandExecutor {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static final Map<UUID, Long> lastSent = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!Conclave.enabled()) return true;
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission(Conclave.permBase())) {
            p.sendMessage(Conclave.msg("no-permission"));
            return true;
        }

        if (label.equalsIgnoreCase("staffchattoggle") || args.length == 0) {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            Byte current = pdc.get(Conclave.get().toggleKey(), PersistentDataType.BYTE);
            byte newValue = (byte) ((current != null && current == 1) ? 0 : 1);
            pdc.set(Conclave.get().toggleKey(), PersistentDataType.BYTE, newValue);
            p.sendMessage(Conclave.msg(newValue == 1 ? "toggle-on" : "toggle-off"));
        } else {
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            Byte state = pdc.get(Conclave.get().toggleKey(), PersistentDataType.BYTE);
            if (state == null || state != 1) {
                p.sendMessage(Conclave.msg("disabled"));
                return true;
            }

            String message = String.join(" ", args);

            int maxLen = Conclave.maxLength();
            if (maxLen > 0 && message.length() > maxLen) {
                p.sendMessage(Conclave.msg("too-long", "{max}", String.valueOf(maxLen)));
                return true;
            }

            int cooldown = Conclave.cooldownSeconds();
            if (cooldown > 0 && !p.hasPermission(Conclave.permBypassCooldown())) {
                Long last = lastSent.get(p.getUniqueId());
                long elapsed = (System.currentTimeMillis() - (last != null ? last : 0)) / 1000;
                if (elapsed < cooldown) {
                    p.sendMessage(Conclave.msg("cooldown", "{seconds}", String.valueOf(cooldown - elapsed)));
                    return true;
                }
            }
            lastSent.put(p.getUniqueId(), System.currentTimeMillis());

            String safeMessage = message.replace("<", "&lt;");
            String format = Conclave.chatFormat()
                    .replace("{player}", p.name().toString())
                    .replace("{message}", safeMessage);

            Component formatted = MM.deserialize(format);

            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.hasPermission(Conclave.permBase())) continue;
                other.sendMessage(formatted);
            }

            if (Conclave.consoleLog()) {
                Conclave.get().getLogger().info("[StaffChat] " + p.getName() + ": " + message);
            }
        }

        return true;
    }
}