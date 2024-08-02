package de.itsuhr.spawnSystem.utils;

import de.itsuhr.spawnSystem.SpawnSystem;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

public class MessageLoader {
    Server server = SpawnSystem.getInstance().getServer();
    ConsoleCommandSender consoleCommandSender = server.getConsoleSender();

    public void startConsole() {
        consoleCommandSender.sendMessage("§7------------§8< §9§lSpawnSystem §8>§7------------");
        consoleCommandSender.sendMessage("§6Author: ItsUhr");
        consoleCommandSender.sendMessage("§bVersion: 1.20.4");
        consoleCommandSender.sendMessage("§aDas Plugin wurde erfolgreich §2Aktiviert");
        consoleCommandSender.sendMessage("§7------------§8< §9§lSpawnSystem §8>§7------------");
        System.out.println("Halloo!");
    }

    public void stopConsole() {
        consoleCommandSender.sendMessage("§7------------§8< §9§lSpawnSystem §8>§7------------");
        consoleCommandSender.sendMessage("§6Author: ItsUhr");
        consoleCommandSender.sendMessage("§bVersion: 1.20.4");
        consoleCommandSender.sendMessage("§cDas Plugin wurde leider §4Deaktiviert");
        consoleCommandSender.sendMessage("§7------------§8< §9§lSpawnSystem §8>§7------------");
        System.out.println("Byee!");
    }

}
