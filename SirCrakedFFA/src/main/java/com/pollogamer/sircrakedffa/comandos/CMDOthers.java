package com.pollogamer.sircrakedffa.comandos;

import com.pollogamer.sircrakedffa.manager.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDOthers implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("ffa")) {
            player.teleport(Lang.ffa);
            player.sendMessage(com.pollogamer.sircrakedserver.utils.Lang.prefix + "Has sido mandado a FFA Clasico");
        } else if (cmd.getName().equalsIgnoreCase("buildffa")) {
            player.teleport(Lang.buildffa);
            player.sendMessage(com.pollogamer.sircrakedserver.utils.Lang.prefix + "Has sido mandado a BuildFFA");
        } else if (cmd.getName().equalsIgnoreCase("comboffa")) {
            player.teleport(Lang.comboffa);
            player.sendMessage(com.pollogamer.sircrakedserver.utils.Lang.prefix + "Has sido mandado a ComboFFA");
        }
        return true;
    }
}
