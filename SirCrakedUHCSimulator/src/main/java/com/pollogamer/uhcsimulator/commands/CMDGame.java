package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.uhcsimulator.task.LobbyTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDGame implements CommandExecutor {

    private String info = "§6§lUHCSimulator §7» §aUtiliza /game start";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only be executed by the Player");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcsimulator.game")) {
            player.sendMessage(Lang.noperm);
            return true;
        }
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "start":
                    if (com.pollogamer.uhcsimulator.extras.Lang.forcestart || com.pollogamer.uhcsimulator.extras.Lang.starting || com.pollogamer.uhcsimulator.extras.Lang.started) {
                        player.sendMessage("§6§lUHCSimulator §7» §aEsta mamada ya esta iniciado we xd");
                    } else {
                        Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEsta mamada se forzo a iniciar xd");
                        com.pollogamer.uhcsimulator.extras.Lang.forcestart = true;
                        com.pollogamer.uhcsimulator.extras.Lang.starting = true;
                        new LobbyTask();
                    }
                    break;
                default:
                    player.sendMessage(info);
                    break;
            }
        } else {
            player.sendMessage(info);
        }
        return true;
    }
}
