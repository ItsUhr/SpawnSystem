package de.itsuhr.spawnSystem.listener;

import de.itsuhr.spawnSystem.SpawnSystem;
import de.itsuhr.spawnSystem.utils.Data;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {
    Data data = SpawnSystem.getInstance().getData();
    private final SpawnSystem instance;

    public RespawnListener(SpawnSystem instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        try {
            if (data.playerRespawnSpawn == true) {
                event.setRespawnLocation(instance.getSpawnLocation());
            }
        } catch (Exception e) {
            player.sendMessage(data.prefix + data.spawnNotSet);
            if (data.sendActionBar == true) {
                instance.getSendActionBar(player, data.spawnNotSet);
            }

            if (data.soundNo == true) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10,1);
            }
        }

    }

}
