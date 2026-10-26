package me.serenity.conclave.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.serenity.conclave.Conclave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class StaffChatListener implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission(Conclave.permBase())) return;

        PersistentDataContainer pdc = p.getPersistentDataContainer();
        Byte state = pdc.get(Conclave.get().toggleKey(), PersistentDataType.BYTE);
        if (state == null || state != 1) return;

        if (!Conclave.enabled()) return;

        e.setCancelled(true);

        Component formatted = MM.deserialize(
                Conclave.chatFormat(),
                Placeholder.component("player", p.displayName()),
                Placeholder.component("message", e.message())
        );

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (!other.hasPermission(Conclave.permBase())) continue;
            other.sendMessage(formatted);
        }

        if (Conclave.consoleLog()) {
            Conclave.get().getLogger().info("[StaffChat] " + p.getName() + ": " + e.message());
        }
    }
}