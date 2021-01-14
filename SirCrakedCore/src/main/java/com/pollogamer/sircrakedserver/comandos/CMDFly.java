package com.pollogamer.sircrakedserver.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDFly implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        String prefix = "§4§lSir§1§lCraked §7§l» §a";
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.fly")) {
            p.sendMessage(prefix + "§aNo tienes permiso para ejecutar ese comando");
            return true;
        }
        if (args.length == 0) {
            if (p.getAllowFlight()) {
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(prefix + "§aHas desactivado el modo de vuelo");
            } else {
                p.setAllowFlight(true);
                p.sendMessage(prefix + "§aHas activado el modo de vuelo");
            }
            return true;
        } else if (args.length == 1) {
            if (!p.hasPermission("sircraked.fly.others")) {
                p.sendMessage(prefix + "§aNo tienes permiso para ejecutar ese comando");
                return true;
            }

            Player obj = Bukkit.getPlayer(args[0]);
            if (obj == null) {
                p.sendMessage(prefix + "§aEl jugador " + args[0] + " no esta conectado!");
                return true;
            }

            if (obj.getAllowFlight()) {
                obj.setAllowFlight(false);
                obj.setFlying(false);
                p.sendMessage(prefix + "§aModo de vuelo desactivado para " + obj.getName());
                obj.sendMessage(prefix + "§aModo de vuelo desactivado por " + p.getName());
            } else {
                obj.setAllowFlight(true);
                obj.setFlying(true);
                p.sendMessage(prefix + "§aModo de vuelo activado para " + obj.getName());
                obj.sendMessage(prefix + "§aModo de vuelo activado por " + p.getName());
            }
        } else {
            p.sendMessage(prefix + "Utiliza /fly o /fly <Jugador>");
        }

        return true;
    }
}
