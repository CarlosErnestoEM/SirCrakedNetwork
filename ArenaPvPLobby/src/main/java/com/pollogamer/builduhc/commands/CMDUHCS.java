package com.pollogamer.builduhc.commands;

import com.pollogamer.builduhc.gui.MenuListServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDUHCS implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the console");
            return true;
        }
        Player player = (Player) sender;
        new MenuListServer(player, "uhcsimulator");
        return true;
    }
}
