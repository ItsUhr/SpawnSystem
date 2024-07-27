package de.itsuhr.spawnSystem.listener;

import de.itsuhr.spawnSystem.SpawnSystem;
import de.itsuhr.spawnSystem.utils.Data;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private Data data = SpawnSystem.getInstance().getData();
    private final SpawnSystem instance;

    public JoinListener(SpawnSystem instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (data.playerAlwaysJoinSpawn == true) {
            try {
                player.teleport(instance.getSpawnLocation());
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

        if (data.playerFirstJoinSpawn == true) {
            if (!player.hasPlayedBefore()) {
                try {
                    player.teleport(instance.getSpawnLocation());
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

    }
}
