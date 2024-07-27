package de.itsuhr.spawnSystem.commands;

import de.itsuhr.spawnSystem.SpawnSystem;
import de.itsuhr.spawnSystem.utils.Data;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SetspawnCommand implements CommandExecutor, TabCompleter {
    private Data data = SpawnSystem.getInstance().getData();
    private final SpawnSystem instance;

    public SetspawnCommand(SpawnSystem instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(data.prefix + instance.getData().consoleSender);
            return true;
        }

        if (!player.hasPermission(data.setSpawnPermission)) {
            player.sendMessage(data.prefix + data.noPerms);
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.noPerms);
            }
            if (data.soundNo == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
            }

            return true;
        }

        if (args.length > 0) {
            player.sendMessage(data.prefix + data.usage + "setspawn");
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.usage + "setspawn");
            }
            if (data.soundNo == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
            }
            return true;
        }

        if (args.length == 0) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(instance.getFileSpawnLocation());
            config.set("Spawn.World", player.getWorld().getName());
            config.set("Spawn.X", player.getLocation().getX());
            config.set("Spawn.Y", player.getLocation().getY());
            config.set("Spawn.Z", player.getLocation().getZ());
            config.set("Spawn.Yaw", player.getLocation().getYaw());
            config.set("Spawn.Pitch", player.getLocation().getPitch());
            try {
                config.save(instance.getFileSpawnLocation());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage(data.prefix + data.setSpawnMessage);
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.setSpawnMessage);
            }
            if (data.soundYes == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 10,1);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Arrays.asList("");
    }
}
