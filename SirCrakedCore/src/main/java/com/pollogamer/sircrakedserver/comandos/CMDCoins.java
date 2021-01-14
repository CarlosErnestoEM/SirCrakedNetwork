package com.pollogamer.sircrakedserver.comandos;

import com.pollogamer.sircrakedserver.coins.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDCoins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("coins")) {
            if (args.length != 1) {
                sender.sendMessage("§4§lSir§b§lCoins §7» §cTienes §a" + CoinsAPI.getCoins((Player) sender) + " §4§lSir§b§lCoins");
                return true;
            }

            Player obj = Bukkit.getPlayer(args[0]);
            sender.sendMessage("§4§lSir§b§lCoins §7» §cLos coins de §a" + obj.getName() + " §cson §a" + CoinsAPI.getCoins(obj));
        }

        if (cmd.getName().equalsIgnoreCase("addcoins")) {
            if (!sender.hasPermission("sircraked.addcoins")) {
                sender.sendMessage("§4§lSir§b§lCoins §7» §cNo puedes hacer eso!");
            } else {
                if (args.length != 2) {
                    sender.sendMessage("§4§lSir§b§lCoins §7» §cUsa /addcoins (jugador) (cantidad)");
                    return true;
                }

                Player obj = (Player) Bukkit.getOfflinePlayer(args[0]);
                int coins = Integer.parseInt(args[1]);
                sender.sendMessage("§4§lSir§b§lCoins §7» §cHas añadido §a" + coins + " §ca §a" + obj.getName());
                CoinsAPI.addCoins(obj, coins);
            }
        }

        if (cmd.getName().equalsIgnoreCase("removecoins")) {
            if (!sender.hasPermission("sircraked.removecoins")) {
                sender.sendMessage("§4§lSir§a§lCoins §7» §cNo puedes hacer eso!");
            } else {
                if (args.length != 2) {
                    sender.sendMessage("§4§lSir§b§lCoins §7» §cUsa /removecoins (jugador) (cantidad)");
                    return true;
                }

                Player obj = (Player) Bukkit.getOfflinePlayer(args[0]);
                int coins = Integer.parseInt(args[1]);
                sender.sendMessage("§4§lSir§b§lCoins §7» §cHas removido §a" + coins + " §cSirCoins a §a" + obj.getName());
                CoinsAPI.removeCoins(obj, coins);
            }
        }

        if (cmd.getName().equalsIgnoreCase("setcoins")) {
            if (!sender.hasPermission("sircraked.setcoins")) {
                sender.sendMessage("§4§lSir§b§lCoins §7» §cNo puedes hacer eso!");
            } else {
                if (args.length != 2) {
                    sender.sendMessage("§4§lSir§b§lCoins §7» §cUsa /setcoins (jugador) (cantidad)");
                    return true;
                }

                Player obj = (Player) Bukkit.getOfflinePlayer(args[0]);
                int coins = Integer.parseInt(args[1]);
                sender.sendMessage("§4§lSir§b§lCoins §7» §cHas seteado §a" + coins + " §cSirCoins a §a" + obj.getName());
                CoinsAPI.setCoins(obj, coins);
            }
        }

        return true;
    }
}
