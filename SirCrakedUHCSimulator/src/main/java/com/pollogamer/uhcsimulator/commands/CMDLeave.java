package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.uhcsimulator.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDLeave implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the console!");
            return true;
        }
        Player player = (Player) sender;
        PlayerManager.sendToServer(player, "HUB-1");
        return true;
    }
}
