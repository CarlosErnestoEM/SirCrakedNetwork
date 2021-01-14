package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.task.PreGameTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMDReroll implements CommandExecutor {

    private List<Player> using = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the player!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcsimulator.reroll")) {
            player.sendMessage(com.pollogamer.sircrakedserver.utils.Lang.noperm);
            return true;
        }
        if (PreGameTask.enabled) {
            if (!using.contains(player)) {
                if (Lang.players.contains(player)) {
                    Principal.getPlugin().getPlayerInventory().setAllPlayer(player);
                    player.sendMessage("§6§lUHCSimulator §7» §aHas cambiado tu kit");
                    using.add(player);
                } else {
                    player.sendMessage("§6§lUHCSimulator §7» §aEstas pendejo? No hagas mamadas compa");
                }
            } else {
                player.sendMessage("§6§lUHCSimulator §7» §aEstas pendejo? No hagas mamadas compa");
            }
        } else {
            player.sendMessage("§6§lUHCSimulator §7» §aEstas pendejo? No hagas mamadas compa");
        }
        return true;
    }
}
