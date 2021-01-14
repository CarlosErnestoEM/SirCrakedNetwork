package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class CMDGlobal extends Command {

    public CMDGlobal() {
        super("global", "bungeecord.command.alert");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§4§lSir§1§lCraked §7» §aNecesitas poner un mensaje");
        } else {
            String message = "§4§LSir§1§lCraked §7» §c" + StringUtils.join(args, 0, " ");
            ProxyServer.getInstance().broadcast(message);
        }
    }
}
