package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.manager.ServerCreator;

public class CMDCreateServer extends WrapperCommand {

    public CMDCreateServer() {
        super("createserver", new String[]{"cs"}, "Create a server", "Create a server type using 'createserver <ServerType>'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            String svtype = args[1];
            ServerCreator.createServer(svtype);
        } else {
            format("ERROR", getUsage());
        }
    }
}
