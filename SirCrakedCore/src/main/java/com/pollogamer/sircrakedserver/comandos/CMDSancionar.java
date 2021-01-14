package com.pollogamer.sircrakedserver.comandos;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.ui.PunishUIMainPage;
import com.pollogamer.sircrakedserver.sanciones.ui.PunishUIMainPageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDSancionar implements CommandExecutor {

    private final SirCrakedCore plugin;

    public CMDSancionar(SirCrakedCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("s")) {
            if (!sender.hasPermission("sircraked.s")) {
                sender.sendMessage("§4§lSir§1§lCraked §7» §cNecesitas el rango §b§lHELPER §Co superior");
                return true;
            }

            if (args.length != 1) {
                p.sendMessage("§4§lSir§1§lCraked §7» §cUtiliza /s (Usuario)");
                return true;
            }

            if (p.hasPermission("sircraked.s.advanced")) {
                new PunishUIMainPage(plugin, p, args[0]);
            } else {
                new PunishUIMainPageHelper(plugin, p, args[0]);
            }
            return true;
        }
        return false;
    }
}
