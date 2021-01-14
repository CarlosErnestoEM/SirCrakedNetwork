package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import com.pollogamer.proxy.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMDDispatch extends Command {

    public CMDDispatch() {
        super("dispatch", "sircraked.dispatch");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            String sv = args[0];
            String command = StringUtils.join(args, 1, " ");
            Principal.getPlugin().getWrapperConnector().sendPacket("command " + sv + " " + command);
            sender.sendMessage(Principal.getPlugin().getPrefix() + "Se envio el paquete...");
        } else {
            sender.sendMessage(Principal.getPlugin().getPrefix() + "Por favor utiliza /dispatch <Servidor||GroupServer||All||Server1:Server2> <Comando>");
        }
    }
}
