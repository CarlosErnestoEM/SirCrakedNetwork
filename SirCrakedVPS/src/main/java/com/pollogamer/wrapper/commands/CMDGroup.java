package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.objects.GroupServer;

public class CMDGroup extends WrapperCommand {

    public CMDGroup() {
        super("group", new String[]{"groups", "servergroup", "servergroup"}, "Revisa la lista e informacion de los grupos", "Usage 'group <List||Info> <ServerGroup>'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            String action = args[1];
            switch (action.toLowerCase()) {
                case "list":
                    if (GroupServer.getGroupServer().size() == 0) {
                        format("ERROR", "Any group server loaded!");
                    } else {
                        format(getCommandName(), "Server groups...");
                        format(getCommandName(), getGroupList());
                    }
                    break;
                default:
                    format("ERROR", getUsage());
                    break;
            }
        } else if (args.length == 3) {
            String action = args[1];
            String groupServerS = args[2];
            switch (action.toLowerCase()) {
                case "info":
                    GroupServer groupServer = GroupServer.getGroupServer(groupServerS);
                    if (groupServer != null) {
                        format(getCommandName(), "Info from group " + groupServerS);
                        format(getCommandName(), "Available servers: " + getServersName(groupServer));
                        format(getCommandName(), "Total Servers of group: " + groupServer.getMcServers().size());
                    } else {
                        format("ERROR", "The group server " + groupServerS + " doesnt exist!");
                    }
                    break;
                default:
                    format("ERROR", getUsage());
                    break;
            }
        } else {
            format("ERROR", getUsage());
        }
    }

    public String getServersName(GroupServer groupServer) {
        String servers = "";
        for (int i = 0; i < groupServer.getMcServers().size(); i++) {
            servers += groupServer.getMcServers().get(i).getServerName();
            if ((i + 1) < groupServer.getMcServers().size()) {
                servers += ", ";
            }
        }
        return servers;
    }

    public String getGroupList() {
        String servers = "";
        int added = 0;
        for (String s : GroupServer.getGroupServer().keySet()) {
            GroupServer groupServer = GroupServer.getGroupServer().get(s);
            servers += groupServer.getGroupName();
            if ((added++ + 1) < GroupServer.getGroupServer().size()) {
                servers += ", ";
            }
        }
        return servers;
    }
}
