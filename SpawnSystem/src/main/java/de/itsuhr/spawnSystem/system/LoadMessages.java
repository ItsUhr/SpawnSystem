package de.itsuhr.spawnSystem.system;

import de.itsuhr.spawnSystem.SpawnSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoadMessages {

    private Data data = SpawnSystem.getInstance().getData();

    public void loadMessage() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(SpawnSystem.getInstance().getFile());

        data.prefix = config.getString("prefix");
        data.consoleSender = config.getString("consoleSender");
        data.noPerms = config.getString(("noPerms"));
        data.usage = config.getString("usage");

        data.soundNo = config.getBoolean("soundNo");
        data.soundYes = config.getBoolean("soundYes");
        data.sendActionBar = config.getBoolean("sendActionBar");

        data.setSpawnPermission = config.getString("setSpawnPermission");
        data.spawnPermission = config.getString("spawnPermission");
        data.spawnReloadPermission = config.getString("spawnReloadPermission");

        data.setSpawnMessage = config.getString("setSpawnMessage");
        data.spawnTeleportMessage = config.getString("spawnTeleportMessage");

        data.countdown = config.getInt("countdown");
        data.countdown_message = config.getString("countdown_message");

        data.spawnNotSet = config.getString("spawnNotSet");

        data.playerRespawnSpawn = config.getBoolean("playerRespawnSpawn");
        data.playerFirstJoinSpawn = config.getBoolean("playerFirstJoinSpawn");
        data.playerAlwaysJoinSpawn = config.getBoolean("playerAlwaysJoinSpawn");

        data.reloadMessage = config.getString("reloadMessage");
    }

    public void loadSpawnMessage() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(SpawnSystem.getInstance().getFileSpawnLocation());
        data.world = config.getString("Spawn.World");
        data.x = config.getDouble("Spawn.X");
        data.y = config.getDouble("Spawn.Y");
        data.z = config.getDouble("Spawn.Z");
        data.yaw = (float) config.getDouble("Spawn.Yaw");
        data.pitch = (float) config.getDouble("Spawn.Pitch");
    }


}

