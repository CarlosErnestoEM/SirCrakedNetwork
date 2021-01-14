package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.uhcsimulator.vote.scenarios.AbstractScenario;
import com.pollogamer.uhcsimulator.vote.scenarios.drop.DropsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDDrops implements CommandExecutor {

    private String help = "§6§lUHCSimulator §7» §aUtiliza /drops addvotes|removevotes <DropName> <Cantidad>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only by executed by the console!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcsimulator.drops")) {
            player.sendMessage(com.pollogamer.sircrakedserver.utils.Lang.noperm);
            return true;
        }
        if (args.length == 3) {
            AbstractScenario abstractScenario = getScenario(args[1]);
            if (abstractScenario != null) {
                if (args[0].toLowerCase().equals("addvotes")) {
                    int number;
                    try {
                        number = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage("§6§LUHCSimulator §7» §aPor favor introduce un numero valido!");
                        return true;
                    }
                    abstractScenario.addVotes(number);
                    player.sendMessage("§6§LUHCSimulator §7» §aHas añadido " + number + (number == 1 ? " voto" : " votos") + " a el scenario " + abstractScenario.getName());
                } else if (args[0].toLowerCase().equals("removevotes")) {
                    int number;
                    try {
                        number = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage("§6§LUHCSimulator §7» §aPor favor introduce un numero valido!");
                        return true;
                    }
                    abstractScenario.removeVotes(number);
                    player.sendMessage("§6§LUHCSimulator §7» §aHas removido " + number + (number == 1 ? " voto" : " votos") + " a el scenario " + abstractScenario.getName());
                } else {
                    player.sendMessage(help);
                }
            } else {
                player.sendMessage("§6§LUHCSimulator §7» §aPor favor introduce un scenario valido!");
            }
        } else {
            player.sendMessage(help);
        }

        return true;
    }

    public AbstractScenario getScenario(String string) {
        for (AbstractScenario abstractScenario : DropsManager.drops) {
            if (abstractScenario.getName().toLowerCase().equals(string.toLowerCase())) {
                return abstractScenario;
            }
        }
        return null;
    }
}
