package com.pollogamer.proxy.comandos;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.PlayerCommand;

public class CMDFind extends PlayerCommand {

    public CMDFind() {
        super("find", "bungeecord.command.find");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§4§lSir§1§lCraked §7» §aPor favor pon un nombre de usuario");
        } else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
            if ((player == null) || (player.getServer() == null)) {
                sender.sendMessage("§4§lSir§1§lCraked §7» §aEl usuario " + args[0] + " no esta conectado");
            } else {
                sender.sendMessage("§4§lSir§1§lCraked §7» §aEl usuario " + args[0] + " esta conectado en §6" + player.getServer().getInfo().getName());
            }
        }
    }
}
