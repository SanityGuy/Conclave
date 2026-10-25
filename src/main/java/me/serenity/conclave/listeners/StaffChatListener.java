package me.serenity.conclave.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.serenity.conclave.Conclave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;

public class StaffChatListener implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMessage(AsyncChatEvent e) {
        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Conclave.getPlugin(Conclave.class), "staffchat_toggle");
        Byte state = pdc.get(key, PersistentDataType.BYTE);

        if (state == null || state != 1) return;
        if (!p.hasPermission("conclave.staffchat")) return;

        e.setCancelled(true);

        Component formatted = MM.deserialize("<gray>[<blue><bold>SC</blue></gray>] <reset>" + p.name() + " <blue>> <reset>" + e.message());

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (!other.hasPermission("conclave.staffchat")) continue;
            other.sendMessage(formatted);
        }
    }
}
