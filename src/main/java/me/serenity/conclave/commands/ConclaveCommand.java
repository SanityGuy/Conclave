package me.serenity.conclave.commands;

import me.serenity.conclave.Conclave;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.logging.Level;

public class ConclaveCommand implements CommandExecutor {

    private static final MiniMessage MM = MiniMessage.miniMessage();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        String subCommand = args.length == 0 ? "help" : args[0].toLowerCase();

        switch (subCommand){
            case "help" -> {
                if (!sender.hasPermission("conclave.help")) { sender.sendMessage(Conclave.prefixed(Conclave.msg("no-permission"))); return true; }

                sender.sendMessage(Conclave.prefixed(MM.deserialize(
                        """
                                
                                <#00E5FF><bold>Conclave Commands</bold>
                                
                                <gray>/conclave help <dark_gray>» <white>Show commands
                                <gray>/conclave version <dark_gray>» <white>Plugin information
                                <gray>/conclave reload <dark_gray>» <white>Reload configuration
                                """)));
            }

            case "version" -> {
                if (!sender.hasPermission("conclave.version")) { sender.sendMessage(Conclave.prefixed(Conclave.msg("no-permission"))); return true; }
                sender.sendMessage(Conclave.prefixed(MM.deserialize(
                        "\n<gray>Version: <#00E5FF>" + Conclave.get().getPluginMeta().getVersion() +
                                "\n<gray>Author: <white>SouyanDev" +
                                "\n<gray>Website: <#00E5FF>https://www.souyande.xyz\n")));
            }


            case "reload" -> {
                if (!sender.hasPermission("conclave.reload")) {
                    sender.sendMessage(Conclave.prefixed(
                            Conclave.msg("no-permission")
                    ));
                    return true;
                }

                try {
                    Conclave plugin = Conclave.get();
                    File configFile = new File(plugin.getDataFolder(),"config.yml");
                    YamlConfiguration validation = new YamlConfiguration();

                    try (Reader reader = Files.newBufferedReader(configFile.toPath())) {
                        validation.load(reader);
                    }

                    plugin.reloadConfig();

                    sender.sendMessage(Conclave.prefixed(
                            MM.deserialize("<#00E5FF>Configuration reloaded successfully.")));
                } catch (IOException | InvalidConfigurationException e) {
                    Conclave.get().getLogger().log(Level.SEVERE,
                            "Failed to reload config.yml. Check the file for errors.", e);

                    sender.sendMessage(Conclave.prefixed(MM.deserialize(
                                    "<red>Failed to reload config.yml. "
                                            + "Check the server console for details.</red>")));
                }
            }

            default -> sender.sendMessage(Conclave.prefixed(MM.deserialize("<red>Unknown subcommand. Use <white>/conclave help")));
        }

        return true;
    }
}
