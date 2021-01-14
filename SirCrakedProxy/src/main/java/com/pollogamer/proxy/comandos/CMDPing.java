package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDPing extends Command {

    public CMDPing() {
        super("ping");
    }

    public void execute(CommandSender cs, String[] args) {
        if ((cs instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) cs;
            if (args.length == 0) {
                p.sendMessage(new TextComponent(Principal.prefixping + "§bTu ping es: §6" + p.getPing() + " §6ms"));
                return;
            }

            if (args.length == 1) {
                ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(args[0]);
                if (pp == null) {
                    p.sendMessage(new TextComponent("§cEl Jugador " + args[0] + " no esta conectado"));
                    return;
                }
                p.sendMessage(new TextComponent(Principal.prefixping + "§bEl ping de " + pp.getName() + " §bes de: §6" + pp.getPing() + "§6ms"));
            }
        }
    }
}
