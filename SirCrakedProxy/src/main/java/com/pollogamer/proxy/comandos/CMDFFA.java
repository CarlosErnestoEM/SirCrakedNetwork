package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDFFA extends Command {

    public CMDFFA() {
        super("FFA");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer p = (ProxiedPlayer) commandSender;
        p.connect(Principal.getPlugin().getProxy().getServerInfo("FFA"));
    }
}
