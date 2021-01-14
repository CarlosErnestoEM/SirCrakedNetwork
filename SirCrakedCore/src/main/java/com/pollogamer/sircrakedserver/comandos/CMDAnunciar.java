package com.pollogamer.sircrakedserver.comandos;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDAnunciar implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("anunciar")) {
            if (!sender.hasPermission("sircraked.anunciar")) {
                sender.sendMessage(ChatColor.RED + "§4§lSir§1§lCraked §7§l» §cNo puedes hacer eso");
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usa: /anunciar <texto>");
                return true;
            }

            String message = StringUtils.join(args, " ", 0, args.length);
            Bukkit.broadcastMessage("§4§lSir§1§lCraked §7§l» §c" + message.replace("&", "§"));
        }
        return true;
    }
}
