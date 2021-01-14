package com.pollogamer.sircrakedserver.comandos;

import com.pollogamer.sircrakedserver.troll.GUITroll;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDTroll implements CommandExecutor {

    private String usage = Lang.prefix + "Utiliza /troll [Usuario]";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only be executed by the player");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("sircraked.troll")) {
            player.sendMessage(Lang.noperm);
            return true;
        }
        if (args.length == 1) {
            Player trolled = Bukkit.getPlayer(args[0]);
            if (trolled != null) {
                new GUITroll(player, trolled);
                player.sendMessage(Lang.prefix + "Abriendo menu troll");
            } else {
                player.sendMessage(Lang.prefix + "El jugador " + args[0] + " no esta conectado we");
            }
        } else {
            player.sendMessage(usage);
        }
        return true;
    }
}
