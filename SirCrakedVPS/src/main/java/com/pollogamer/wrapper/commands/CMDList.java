package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.objects.McServer;

public class CMDList extends WrapperCommand {

    public CMDList() {
        super("list", new String[]{"lista"}, "Revisa la lista de servidores", "Usage 'list'");
    }

    @Override
    public void execute(String[] args) {
        if (McServer.getServers().size() == 0) {
            format("ERROR", "Any servers connected! :(");
        } else {
            format(getCommandName(), "List of servers connected...");
            format(getCommandName(), getServers());
            format(getCommandName(), "Total Servers Connected: " + McServer.getServers().size());
        }
    }

    public String getServers() {
        String servers = "";
        for (int i = 0; i < McServer.getServers().size(); i++) {
            servers += McServer.getServers().get(i).getServerName();
            if ((i + 1) < McServer.getServers().size()) {
                servers += ", ";
            }
        }
        return servers;
    }

}
