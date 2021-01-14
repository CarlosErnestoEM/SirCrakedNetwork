package com.pollogamer.tntrun.commands;

import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDGame implements CommandExecutor {


    public void sendMessage(Player p, String s) {
        p.sendMessage(s.replaceAll("&", "§"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("tnttag.game")) {
            sendMessage(p, "&cNo tienes permisos pendejo xd");
            return true;
        }
        if (args.length == 0) {
            sendMessage(p, "&cUtiliza /game start");
            return true;
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "start":
                    if (Lang.starting || Lang.started) {
                        sendMessage(p, "&eEsta mamada ya esta en juego");
                    } else {
                        Lang.forcestart = true;
                        Bukkit.broadcastMessage("§eEsta mamada se forzo a iniciar");
                        new LobbyTask();
                    }
                    break;
                default:
                    sendMessage(p, "&cUtiliza /game start");
                    break;
            }
            return true;
        } else {
            sendMessage(p, "&cUtiliza /game start");
        }
        return true;
    }
}
