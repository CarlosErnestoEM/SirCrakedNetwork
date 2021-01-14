package com.pollogamer.uhcsimulator.commands;

import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.utils.Utils;
import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDUHCS implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The command can only be executed by the player");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("uhcsimulator.admin")) {
            player.sendMessage(Lang.prefix + "No tienes permisos para ejecutar este comando");
            return true;
        }
        if (args.length == 0) {
            sendHelpPage(player);
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "setspawn":
                    try {
                        Principal.getPlugin().getConfig().set("locations.spawn", Utils.serializeLoc(player.getLocation()));
                        player.sendMessage("Has marcado correctamente el spawn");
                        Principal.getPlugin().getConfig().save();
                    } catch (Exception e) {
                        player.sendMessage("§cHa ocurrido 1 error! Por favor revisa la consola");
                        e.printStackTrace();
                    }
                    break;
                case "setup":
                    try {
                        boolean oldvalue = Principal.getPlugin().getConfig().getBoolean("setup");
                        Principal.getPlugin().getConfig().set("setup", (!oldvalue));
                        Principal.getPlugin().getConfig().save();
                        player.sendMessage("Has puesto el valor setup como " + !oldvalue + " se aplicara al reiniciar el juego");
                    } catch (Exception e) {
                        player.sendMessage("§cHa ocurrido 1 error! Por favor revisa la consola");
                        e.printStackTrace();
                    }
                    break;
                default:
                    sendHelpPage(player);
                    break;
            }
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "setelotop":
                    Principal.getPlugin().getConfig().set("holograms.topelo." + args[1], Utils.serializeLoc(player.getLocation()));
                    player.sendMessage("Has marcado correctamente el spawn");
                    Principal.getPlugin().getConfig().save();
                    break;
                case "setkillstop":
                    Principal.getPlugin().getConfig().set("holograms.topkills." + args[1], Utils.serializeLoc(player.getLocation()));
                    player.sendMessage("Has marcado correctamente el spawn");
                    Principal.getPlugin().getConfig().save();
                    break;
                case "setwinstop":
                    Principal.getPlugin().getConfig().set("holograms.topwins." + args[1], Utils.serializeLoc(player.getLocation()));
                    player.sendMessage("Has marcado correctamente el spawn");
                    Principal.getPlugin().getConfig().save();
                    break;
                default:
                    sendHelpPage(player);
                    break;
            }
        } else {
            sendHelpPage(player);
        }
        return true;
    }

    public void sendHelpPage(Player player) {
        player.sendMessage(Lang.prefix + "Utiliza /uhcsimulator setspawn|setup|setelotop|setkillstop|setwinstop");
    }
}
