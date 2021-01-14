package com.pollogamer.sircrakedhub.comandos;

import com.pollogamer.sircrakedhub.manager.PlayerManager;
import com.pollogamer.sircrakedhub.objects.ListServer;
import com.pollogamer.sircrakedserver.objects.GameServer;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDJoin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only be executed by the player");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            ListServer listServer = ListServer.getListServer(args[0]);
            if (listServer != null) {
                GameServer gameServer = listServer.getRandomServer();
                if (gameServer != null) {
                    player.sendMessage(Lang.prefix + "Seras mandado a §e" + gameServer.getBungeename());
                    PlayerManager.sendToServer(player, gameServer.getBungeename());
                } else {
                    player.sendMessage(Lang.prefix + "§cNo encontramos ninguna partida disponible :(");
                }
            } else {
                player.sendMessage(Lang.prefix + "La modalidad " + args[0] + " no existe xd");
            }
        } else {
            player.sendMessage(Lang.prefix + "Utiliza /join [Modalidad]");
        }
        return true;
    }
}
