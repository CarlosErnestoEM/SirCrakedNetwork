package com.pollogamer.sircrakedserver.staffmode;

import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDStp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the player");
            return true;
        }
        Player player = (Player) sender;
        if (StaffMode.isEnabled(player)) {
            if (args.length == 1) {
                Player obj = Bukkit.getPlayer(args[0]);
                if (obj != null) {
                    player.sendMessage(Lang.prefix + "Te haz teletransportado a " + obj.getName());
                    player.teleport(obj.getLocation());
                } else {
                    player.sendMessage(Lang.prefix + "El jugador " + args[0] + " no esta conectado we");
                }
            } else {
                player.sendMessage(Lang.prefix + "Utiliza /stp <Usuario>");
            }
        } else {
            player.sendMessage(Lang.prefix + "No tienes el modo staff activado xd");
        }
        return true;
    }
}
