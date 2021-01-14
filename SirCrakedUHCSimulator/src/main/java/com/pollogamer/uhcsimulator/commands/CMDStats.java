package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.uhcsimulator.manager.PlayerManager;
import com.pollogamer.uhcsimulator.objects.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDStats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the console!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§6§lUHCSimulator §7» §aEl usuario " + args[0] + "no esta conectado!");
            } else {
                UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(target);
                sendStats(uhcPlayer, player);
            }
        } else {
            UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
            sendStats(uhcPlayer, player);
        }
        return true;
    }

    public void sendStats(UHCPlayer uhcPlayer, Player player) {
        player.sendMessage("§cEstadisticas de " + uhcPlayer.getPlayer().getName());
        player.sendMessage("§aPartidas Ganadas: §f" + uhcPlayer.getWins());
        player.sendMessage("§aKills: §f" + uhcPlayer.getKills());
        player.sendMessage("§aPartidas jugadas: §f" + uhcPlayer.getPlayed());
        player.sendMessage("§aELO: §f" + uhcPlayer.getElo());
        player.sendMessage("§aRango: §f" + PlayerManager.getRank(uhcPlayer.getElo()));
    }
}
