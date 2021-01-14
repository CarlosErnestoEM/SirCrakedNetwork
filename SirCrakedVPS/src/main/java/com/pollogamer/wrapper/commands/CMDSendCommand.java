package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.objects.GroupServer;
import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.utils.StringUtils;


public class CMDSendCommand extends WrapperCommand {

    public CMDSendCommand() {
        super("sendcommand", new String[]{"sendcommands", "sendc"}, "Manda comandos a 1 o todos los servidores", "Usage 'sendcommand <Server||All||GroupServer||Server1:Server2> <Command>'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 3) {
            String serverName = args[1];
            if (serverName.equalsIgnoreCase("all")) {
                for (McServer mcServer : McServer.getServers()) {
                    if (!mcServer.isBungee()) {
                        mcServer.sendCommand(getCommand(args));
                    }
                }
                format("CMD-SENDCOMMANDS", "Commands have been sent to all servers!");
            } else {
                String[] svs = serverName.split(":");
                if (serverName.contains(":")) {
                    for (String s : svs) {
                        trySendCommand(args, s);
                    }
                } else {
                    trySendCommand(args, svs[0]);
                }
            }
        } else {
            format("ERROR", getUsage());
        }
    }

    public void trySendCommand(String[] command, String sv) {
        McServer mcServer = Main.getMain().getServer().getServer(sv);
        if (mcServer == null) {
            GroupServer groupServer = GroupServer.getGroupServer(sv);
            if (groupServer != null) {
                groupServer.sendCommand(getCommand(command));
            } else {
                format("ERROR", "The server or GroupServer " + sv + " not exist!");
            }
        } else {
            mcServer.sendCommand(getCommand(command));
            format("CMD-SENDCOMMANDS", "Command has been send to server " + mcServer.getServerName() + "!");
        }
    }

    public String getCommand(String[] args) {
        return StringUtils.join(args, 2, " ");
    }
}
