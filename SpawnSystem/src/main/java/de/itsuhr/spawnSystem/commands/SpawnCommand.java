package de.itsuhr.spawnSystem.commands;

import de.itsuhr.spawnSystem.SpawnSystem;
import de.itsuhr.spawnSystem.utils.Data;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {
    private Data data = SpawnSystem.getInstance().getData();
    private final SpawnSystem instance;

    public SpawnCommand(SpawnSystem instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(data.prefix + data.consoleSender);
            return true;
        }

        if (!player.hasPermission(data.spawnPermission)) {
            player.sendMessage(data.prefix + data.noPerms);
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.noPerms);
            }
            if (data.soundNo == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
            }

            return true;
        }

        if (args.length > 1) {
            player.sendMessage(data.prefix + data.usage + "spawn <reload/author>");
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.usage + "spawn <reload/author>");
            }
            if (data.soundNo == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
            }
            return true;
        }

        if (args.length == 0) {
            try {
                BukkitScheduler scheduler = instance.getServer().getScheduler();
                scheduler.runTaskTimer(instance, new Runnable() {
                    int countdown = data.countdown;

                    @Override
                    public void run() {
                        if (countdown > 0) {
                            player.sendMessage(data.prefix + data.countdown_message.replace("%time%", String.valueOf(countdown)));

                            if (data.sendActionBar == true) {
                                instance.getSendActionBar(player, data.countdown_message.replace("%time%", String.valueOf(countdown)));
                            }

                            if (data.soundYes == true) {
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }

                            countdown--;
                        } else {
                            player.teleport(instance.getSpawnLocation());
                            player.sendMessage(data.prefix + data.spawnTeleportMessage);
                            if (data.sendActionBar == true) {
                                instance.getSendActionBar(player, data.spawnTeleportMessage);
                            }

                            if (data.soundYes == true) {
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 10, 1);
                            }
                            scheduler.cancelTasks(instance);
                        }
                    }
                }, 0L, 20L);
            } catch (Exception e) {
                player.sendMessage(data.prefix + data.spawnNotSet);
                if (data.sendActionBar == true) {
                    instance.getSendActionBar(player, data.spawnNotSet);
                }

                if (data.soundNo == true) {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
                }
            }

            return true;
        }

        if (args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "reload" -> {
                    if (!player.hasPermission(data.spawnReloadPermission)) {
                        player.sendMessage(data.prefix + data.noPerms);
                        if (data.sendActionBar == true) {
                            instance.getSendActionBar(player, data.noPerms);
                        }
                        if (data.soundNo == true) {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
                        }

                        return true;
                    }
                    try {
                        FileConfiguration configSpawn = YamlConfiguration.loadConfiguration(instance.getFileSpawnLocation());
                        FileConfiguration config = YamlConfiguration.loadConfiguration(instance.getFile());
                        configSpawn.load(instance.getFileSpawnLocation());
                        config.load(instance.getFile());
                        player.sendMessage(data.prefix + data.reloadMessage);
                        if (data.sendActionBar == true) {
                            instance.getSendActionBar(player, data.reloadMessage);
                        }
                        if (data.soundYes == true) {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 10,1);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }

                }
                case "author" -> {
                    player.sendMessage(data.prefix + "§7------------§8< §9§lSpawnSystem §8>§7------------");
                    player.sendMessage(data.prefix + "§6Author: ItsUhr");
                    player.sendMessage(data.prefix + "§bVersion: 1.20.4");
                    player.sendMessage(data.prefix + "§aIch hoffe ihr habt Spaß! <3");
                    player.sendMessage(data.prefix + "§7------------§8< §9§lSpawnSystem §8>§7------------");
                }
                default -> {
                    player.sendMessage(data.prefix + data.usage + "spawn <reload/author>");
                    if (data.sendActionBar == true) {
                        instance.getSendActionBar(player, data.usage + "spawn <reload/author>");
                    }
                    if (data.soundNo == true) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
                    }
                    return true;
                }
            }

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if (player.hasPermission(data.spawnReloadPermission)) {
                list.add("reload");
            }
            list.add("author");
        }
        return list;
    }
}
