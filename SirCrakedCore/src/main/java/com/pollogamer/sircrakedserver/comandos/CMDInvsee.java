package com.pollogamer.sircrakedserver.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class CMDInvsee implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (!sender.hasPermission("sircraked.invsee")) {
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cNo tienes permiso para hacer eso");
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage("§cUsa /invsee (jugador)");
                return true;
            }

            Player obj = Bukkit.getPlayer(args[0]);
            if (obj == null) {
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a " + args[0] + " §cno esta conectado!");
                return true;
            }

            PlayerInventory ti = obj.getInventory();
            p.openInventory(ti);
            sender.sendMessage("§4§lSir§1§lCraked §7§l» §cHas abierto el inventario de  §a" + obj.getName());
        }

        return true;
    }

}
