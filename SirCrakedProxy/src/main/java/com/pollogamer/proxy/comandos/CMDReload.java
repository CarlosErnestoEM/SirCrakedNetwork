package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import com.pollogamer.proxy.listener.ServerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMDReload extends Command {

    public CMDReload() {
        super("sircrakedreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sircraked.reload")) {
            sender.sendMessage(Principal.getPlugin().getPrefix() + "No puedes hacer eso!");
            return;
        }
        Principal.getPlugin().loadConfig();
        ServerListener.init();
        sender.sendMessage(Principal.getPlugin().getPrefix() + "Plugin recargado!");
    }
}
