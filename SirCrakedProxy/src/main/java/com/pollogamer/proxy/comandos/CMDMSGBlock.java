package com.pollogamer.proxy.comandos;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDMSGBlock extends Command {

    public CMDMSGBlock() {
        super("msgblock", "");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cSolo jugadores");
            return;
        }
        if (CMDMSG.blockmsg.contains(sender)) {
            CMDMSG.blockmsg.remove(sender);
            sender.sendMessage("§4§lSir§1§lCraked §7» §aHas desbloqueado los mensajes");
        } else {
            CMDMSG.blockmsg.add((ProxiedPlayer) sender);
            sender.sendMessage("§4§lSir§1§lCraked §7» §aHas bloqueado los mensajes");
        }
    }
}
