package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDRevive implements CommandExecutor {

    private String usage = "§6§lUHCSimulator §7» §aUtiliza /revive <Jugador>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the player!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcsimulator.revive")) {
            player.sendMessage(Lang.noperm);
            return true;
        }
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (com.pollogamer.uhcsimulator.extras.Lang.players.contains(target)) {
                    player.sendMessage("§6§LUHCSimulator §7» §aEl jugador esta vivo xd");
                    return true;
                } else {
                    if (Principal.getPlugin().getKillManager().getPlayerStorage().existData(target)) {
                        Principal.getPlugin().getSpectatorManager().removeSpectator(target);
                        Principal.getPlugin().getKillManager().getPlayerStorage().setData(target, true);
                        com.pollogamer.uhcsimulator.extras.Lang.players.add(target);
                        player.sendMessage("§6§LUHCSimulator §7» §aHas revivido a " + target.getName());
                        target.sendMessage("§6§lUHCSimulator §7» §aHas sido revivido! Agradescale a " + player.getName() + " xdxd");
                    } else {
                        player.sendMessage("§6§lUHCSimulator §7» §aEl jugador no puede ser revivido ya que no jugo  xd");
                    }
                }
            } else {
                player.sendMessage("§6§LUHCSimulator §7» §aEl jugador " + args[0] + " no esta conectado xc");
            }
        } else {
            player.sendMessage(usage);
        }
        return true;
    }
}
