package de.itsuhr.spawnSystem;

import de.itsuhr.spawnSystem.commands.SetspawnCommand;
import de.itsuhr.spawnSystem.commands.SpawnCommand;
import de.itsuhr.spawnSystem.listener.JoinListener;
import de.itsuhr.spawnSystem.listener.RespawnListener;
import de.itsuhr.spawnSystem.utils.Data;
import de.itsuhr.spawnSystem.utils.MessageLoader;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public final class SpawnSystem extends JavaPlugin {
    @Getter
    private static SpawnSystem instance;
    private final File file = new File("plugins/SpawnSystem", "config.yml");
    private final File fileSpawnLocation = new File("plugins/SpawnSystem", "spawn.yml");
    private MessageLoader messageLoader;
    private Data data;

    @Override
    public void onEnable() {
        instance = this;
        messageLoader = new MessageLoader();
        data = new Data();
        createConfig();
        loadMessage();
        loadSpawnMessage();
        getRegister();

        System.err.print("SpawnSystem ist ein Fehler eingetreten!");
        getLogger().info("SpawnSystem ist Aktiviert!");
        messageLoader.startConsole();
    }

    private void getRegister() {
        register("setspawn", new SetspawnCommand(this));
        register("spawn", new SpawnCommand(this));

        register(new RespawnListener(this));
        register(new JoinListener(this));
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("SpawnSystem ist Deaktiviert!");
        messageLoader.stopConsole();
    }

    private void loadMessage() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        data.prefix = config.getString(ChatColor.translateAlternateColorCodes('&',"prefix"));
        data.consoleSender = config.getString(ChatColor.translateAlternateColorCodes('&',"consoleSender"));
        data.noPerms = config.getString(ChatColor.translateAlternateColorCodes('&',"noPerms"));
        data.usage = config.getString(ChatColor.translateAlternateColorCodes('&',"usage"));

        data.soundNo = config.getBoolean("soundNo");
        data.soundYes = config.getBoolean("soundYes");
        data.sendActionBar = config.getBoolean("sendActionBar");

        data.setSpawnPermission = config.getString(ChatColor.translateAlternateColorCodes('&',"setSpawnPermission"));
        data.spawnPermission = config.getString(ChatColor.translateAlternateColorCodes('&',"spawnPermission"));
        data.spawnReloadPermission = config.getString(ChatColor.translateAlternateColorCodes('&',"spawnReloadPermission"));

        data.setSpawnMessage = config.getString(ChatColor.translateAlternateColorCodes('&',"setSpawnMessage"));
        data.spawnTeleportMessage = config.getString(ChatColor.translateAlternateColorCodes('&',"spawnTeleportMessage"));

        data.countdown = config.getInt("countdown");
        data.countdown_message = config.getString(ChatColor.translateAlternateColorCodes('&',"countdown_message"));

        data.spawnNotSet = config.getString(ChatColor.translateAlternateColorCodes('&',"spawnNotSet"));

        data.playerRespawnSpawn = config.getBoolean("playerRespawnSpawn");
        data.playerFirstJoinSpawn = config.getBoolean("playerFirstJoinSpawn");
        data.playerAlwaysJoinSpawn = config.getBoolean("playerAlwaysJoinSpawn");

        data.reloadMessage = config.getString(ChatColor.translateAlternateColorCodes('&',"reloadMessage"));
    }

    private void loadSpawnMessage() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileSpawnLocation);

        data.world = config.getString("Spawn.World");
        data.x = config.getDouble("Spawn.X");
        data.y = config.getDouble("Spawn.Y");
        data.z = config.getDouble("Spawn.Z");
        data.yaw = (float) config.getDouble("Spawn.Yaw");
        data.pitch = (float) config.getDouble("Spawn.Pitch");
    }

    private void createConfig() {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                if (!config.contains("prefix")) {
                    config.set("prefix", "&8[&e&lSpawn&8] &7");
                }

                if (!config.contains("noPerms")) {
                    config.set("noPerms", "&4Fehler: &cDu hast keine &4Berechtigung &c,dies zu nutzen");
                }

                if (!config.contains("consoleSender")) {
                    config.set("consoleSender", "&4Fehler: &cNur ein Spieler darf dies nutzen!");
                }

                if (!config.contains("usage")) {
                    config.set("usage", "&4Fehler: &cbitte benutze &e/");
                }

                if (!config.contains("soundNo")) {
                    config.set("soundNo", true);
                }

                if (!config.contains("soundYes")) {
                    config.set("soundYes", true);
                }

                if (!config.contains("sendActionBar")) {
                    config.set("sendActionBar", true);
                }

                if (!config.contains("setSpawnPermission")) {
                    config.set("setSpawnPermission", "spawnsystem.commands.setspawn");
                }

                if (!config.contains("spawnPermission")) {
                    config.set("spawnPermission", "spawnsystem.commands.spawn");
                }

                if (!config.contains("spawnReloadPermission")) {
                    config.set("spawnReloadPermission", "spawnsystem.commands.reload");
                }

                if (!config.contains("setSpawnMessage")) {
                    config.set("setSpawnMessage", "&7Der &e&lSpawn &7wurde gesetzt.");
                }

                if (!config.contains("spawnTeleportMessage")) {
                    config.set("spawnTeleportMessage", "&7Du wurdest zum &e&lSpawn &7teleportiert.");
                }

                if (!config.contains("countdown")) {
                    config.set("countdown", 5);
                }

                if (!config.contains("countdown_message")) {
                    config.set("countdown_message", "&7Du wirst in §b&l%time% &7zum &e&lSpawn &7teleportiert.");
                }

                if (!config.contains("spawnNotSet")) {
                    config.set("spawnNotSet", "&4Fehler: &cDer &e&lSpawn &cist nicht vorhanden!");
                }

                if (!config.contains("playerRespawnSpawn")) {
                    config.set("playerRespawnSpawn", true);
                }

                if (!config.contains("playerFirstJoinSpawn")) {
                    config.set("playerFirstJoinSpawn", true);
                }

                if (!config.contains("playerAlwaysJoinSpawn")) {
                    config.set("playerAlwaysJoinSpawn", true);
                }

                if (!config.contains("reloadMessage")) {
                    config.set("reloadMessage", "&7Die &e&lSpawn &b&lConfig &7wurde neugeladen.");
                }

                config.save(file);

                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!fileSpawnLocation.getParentFile().exists()) {
            fileSpawnLocation.getParentFile().mkdirs();
        }

        if (!fileSpawnLocation.exists()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(fileSpawnLocation);

                if (!config.contains("Spawn.World")) {
                    config.set("Spawn.World", "world");
                }

                if (!config.contains("Spawn.X")) {
                    config.set("Spawn.X", 0);
                }

                if (!config.contains("Spawn.Y")) {
                    config.set("Spawn.Y", 100);
                }

                if (!config.contains("Spawn.Z")) {
                    config.set("Spawn.Z", 0);
                }

                if (!config.contains("Spawn.Yaw")) {
                    config.set("Spawn.Yaw", 80);
                }

                if (!config.contains("Spawn.Pitch")) {
                    config.set("Spawn.Pitch", 80);
                }

                config.save(fileSpawnLocation);

                fileSpawnLocation.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void register(String commandName, CommandExecutor executor) {
        this.getCommand(commandName).setExecutor(executor);
    }

    private void register(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public void getSendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public Location getSpawnLocation() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileSpawnLocation);
        World world = getServer().getWorld(config.getString("Spawn.World"));
        double x = config.getDouble("Spawn.X");
        double y = config.getDouble("Spawn.Y");
        double z = config.getDouble("Spawn.Z");
        float yaw = (float) config.getDouble("Spawn.Yaw");
        float pitch = (float) config.getDouble("Spawn.Pitch");
        Location location = new Location(world, x ,y, z ,yaw, pitch);
        return location;
    }
}
