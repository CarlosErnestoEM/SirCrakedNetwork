package com.pollogamer.tnttag.cmd;

import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.extras.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDTNTTag implements CommandExecutor {

    public void sendMessage(Player p, String s) {
        p.sendMessage(s.replaceAll("&", "§"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("tntrun.admin")) {
            sendMessage(p, "&cNo tienes permisos pendejo xd");
            return true;
        }
        if (args.length == 0) {
            sendMessage(p, "&cUtiliza /tnttag setspawn|setlobby|setmin|setmap");
            return true;
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "setspawn":
                    Principal.plugin.getConfig().set("locations.spawn", Utils.serializeLoc(p.getLocation()));
                    Principal.plugin.saveConfig();
                    sendMessage(p, "&aMascaste el spawn!");
                    break;
                case "setlobby":
                    Principal.plugin.getConfig().set("locations.lobby", Utils.serializeLoc(p.getLocation()));
                    sendMessage(p, "&aMarcaste el lobby!");
                    Principal.plugin.saveConfig();
                    break;
                default:
                    sendMessage(p, "&cUtiliza /tnttag setspawn|setlobby|setmin|setmap");
                    break;
            }
        } else if (args.length >= 2 && args.length < 3) {
            switch (args[0].toLowerCase()) {
                case "setmin":
                    Principal.plugin.getConfig().set("minplayers", args[1]);
                    sendMessage(p, "&aLos jugadores minimos son " + args[1]);
                    Principal.plugin.saveConfig();
                    break;
                case "setmap":
                    Principal.plugin.getConfig().set("mapa", args[1]);
                    sendMessage(p, "&aEl mapa se llama " + args[1]);
                    Principal.plugin.saveConfig();
                    break;
                default:
                    sendMessage(p, "&cUtiliza /tnttag setspawn|setlobby|setmin|setmap");
                    break;
            }
        } else {
            sendMessage(p, "&cUtiliza /tnttag setspawn|setlobby|setmin|setmap");
            return true;
        }
        return true;
    }
}
