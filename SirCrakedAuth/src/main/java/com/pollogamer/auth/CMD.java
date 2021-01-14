package com.pollogamer.auth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Principal.getPlugin().getLogger().info("Solo jugador");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.authadmin")) return true;
        if (args.length == 0) {
            p.sendMessage("&aUtiliza /authserver setspawn");
            return true;
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "setspawn":
                    p.sendMessage("&aLocalizacion del spawn marcada!");
                    Utils.setSpawn(p.getLocation());
                    break;
                default:
                    p.sendMessage("&aUtiliza /authserver setspawn");
                    break;
            }
        } else {
            p.sendMessage("&aUtiliza /authserver setspawn");
        }
        return true;
    }
}
