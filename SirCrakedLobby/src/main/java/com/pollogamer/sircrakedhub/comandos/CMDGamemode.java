package com.pollogamer.sircrakedhub.comandos;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDGamemode implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (!sender.hasPermission("sircraked.gm")) {
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cNo tienes permiso para hacer eso");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§cUtiliza 0 para survival, 1 para creativo,2 aventura y 3 espectador");
                return true;
            }
            if (args[0].equalsIgnoreCase("1")) {
                p.setGameMode(GameMode.CREATIVE);
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo creativo!");
                return true;
            }
            if (args[0].equalsIgnoreCase("0")) {
                p.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo survival!");
                return true;
            }
            if (args[0].equalsIgnoreCase("2")) {
                p.setGameMode(GameMode.ADVENTURE);
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo aventura!");
                return true;
            }
            if (args[0].equalsIgnoreCase("3")) {
                p.setGameMode(GameMode.SPECTATOR);
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo espectador!");
                return true;
            }
            sender.sendMessage("§4§lSir§1§lCraked §7§l» §cIntroduce un modo de juego valido");
            if (args.length == 2) {
                Player obj = Bukkit.getPlayer(args[1]);
                if (obj == null) {
                    sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a" + args[1] + " §cNo esta conectado");
                    return true;
                }
                if (args[0].equalsIgnoreCase("1")) {
                    obj.setGameMode(GameMode.CREATIVE);
                    obj.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo creativo!");
                    sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a" + obj.getName() + " §cesta en modo creativo!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("0")) {
                    obj.setGameMode(GameMode.SURVIVAL);
                    obj.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo survival!");
                    sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a" + obj.getName() + " §cesta en modo survival!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("2")) {
                    obj.setGameMode(GameMode.ADVENTURE);
                    obj.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo aventura!");
                    sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a" + obj.getName() + " §cesta en modo aventura!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("3")) {
                    obj.setGameMode(GameMode.SPECTATOR);
                    obj.sendMessage("§4§lSir§1§lCraked §7§l» §cAhora estas en modo espectador!");
                    sender.sendMessage("§4§lSir§1§lCraked §7§l» §cEl jugador §a" + obj.getName() + " §cesta en modo espectador!");
                    return true;
                }
                sender.sendMessage("§4§lSir§1§lCraked §7§l» §cIntroduce un modo de juego valido");
            }
            if (args.length > 3) {
                sender.sendMessage("§cUtiliza 0 para survival, 1 para creativo,2 aventura y 3 espectador");
                return true;
            }
        }
        return false;
    }
}
