package com.pollogamer.sircrakedserver.staffmode;

import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDStaffMode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the Player");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("sircraked.ss")) {
            player.sendMessage(Lang.prefix + "No tienes permiso para hacer esto xd");
            return true;
        }

        StaffMode.toggleStaffMode(player);

        if (args.length == 1) {
            Player obj = Bukkit.getPlayer(args[0]);
            if (obj != null) {
                Bukkit.dispatchCommand(player, "stp " + obj.getName());
            }
        }
        return true;
    }
}
