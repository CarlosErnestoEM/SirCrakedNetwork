package com.pollogamer.sircrakedserver.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDYoutuber implements CommandExecutor {


    public CMDYoutuber() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("youtuberset")) {
            if (!p.hasPermission("sircraked.youtuberset")) {
                sender.sendMessage("§4§lSir§1§lCraked§7§l » §cNo tienes permiso para hacer eso");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage("§cUsa /youtuberset (usuario)");
                return true;
            }

            if (p.hasPermission("sircraked.youtuberset")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + args[0] + " setrank Youtuber");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + args[0] + " prefix &4&lY&F&LT &a");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + args[0] + " priority 8");
                sender.sendMessage("§4§lSir§1§lCraked§7§l » §cEl usuario §a " + args[0] + " §cahora tiene el rango Youtuber!");
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("youtuberquit")) {
            if (!p.hasPermission("sircraked.youtuberset")) {
                sender.sendMessage("§4§lSir§1§lCraked§7§l » §cNo tienes permiso para hacer eso");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage("§cUsa /youtuberquit (usuario)");
                return true;
            }

            if (p.hasPermission("sircraked.youtuberset")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pp user " + args[0] + " setrank Usuario");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + args[0] + " clear");
                sender.sendMessage("§4§lSir§1§lCraked§7§l » §cEl usuario §a " + args[0] + " §cya no tiene el rango Youtuber!");
                return true;
            }
        }

        return false;
    }
}
