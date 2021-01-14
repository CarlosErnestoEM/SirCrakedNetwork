package com.pollogamer.sircrakedhub.comandos;

import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.listener.PlayerListener;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDLobby implements CommandExecutor {

    public void setSpawn(Location l) {
        Principal.plugin.getConfig().set("spawn", com.pollogamer.sircrakedserver.utils.Utils.serializeLoc(l));
        Principal.plugin.saveConfig();
        PlayerListener.init();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo los jugadores pueden ejecutar esto");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.lobby")) {
            //No perms
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("Usa /lobby setspawn");
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setspawn")) {
                setSpawn(p.getLocation());
                p.sendMessage("Spawn marcado");
            } else if (args[0].equalsIgnoreCase("reload")) {
                Principal.plugin.reloadConfig();
                p.sendMessage("Plugin recargado");
            } else {

            }
        } else {
            //Si no es nada de lo anterior
        }

        return true;
    }
}
