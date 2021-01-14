package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.objects.McServer;

public class CMDServer extends WrapperCommand {

    public CMDServer() {
        super("server", new String[]{"addserver"}, "Anade o remueve servidores al BungeeCord", "Usage 'server <Remove|Add> <ServerName> <ServerIP>'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 3) {
            String action = args[1];
            String sv = args[2];
            McServer mcServer = Main.getMain().getServer().getServer(sv);
            if (mcServer != null) {
                switch (action.toLowerCase()) {
                    case "remove":
                        McServer.sendPacketToBungees("server remove " + mcServer.getServerName());
                        break;
                    case "add":
                        McServer.sendPacketToBungees("server add " + mcServer.getServerName() + " " + mcServer.getIP() + ":" + mcServer.getPort());
                        break;
                    default:
                        format("ERROR", getUsage());
                        break;
                }
            } else {
                switch (action.toLowerCase()) {
                    case "remove":
                        McServer.sendPacketToBungees("server remove " + sv);
                        break;
                    default:
                        format("ERROR", getUsage());
                        break;
                }
            }
        } else if (args.length == 4) {
            String action = args[1];
            String sv = args[2];
            if (args[3].contains(":")) {
                String[] ip = args[3].split(":");
                switch (action.toLowerCase()) {
                    case "add":
                        McServer.sendPacketToBungees("server add " + sv + " " + ip[0] + ":" + ip[1]);
                        break;
                    default:
                        format("ERROR", getUsage());
                        break;
                }
            } else {
                format("ERROR", "Please set the ip using IP:PORT");
            }
        } else {
            format("ERROR", getUsage());
        }
    }
}
