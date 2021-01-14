package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.objects.MinigameServer;

public class CMDStop extends WrapperCommand {

    public CMDStop() {
        super("stop", new String[]{"end"}, "Apaga el servidor", "Usage 'stop'");
    }

    @Override
    public void execute(String[] args) {
        format("SYSTEM", "Stopping MiniGame Servers...");
        for (MinigameServer minigameServer : MinigameServer.getServers()) {
            minigameServer.end();
        }
        format("SYSTEM", "Preparing to stop the server");
        Main.getMain().closeAllConnections();
        format("SYSTEM", "Stopping server...");
        Main.getMain().getServer().end();
        format("SYSTEM", "Server stopped...");
        format("SYSTEM", "Clossing redis connection...");
        //Main.getMain().getJedisManager().stop();
        System.exit(0);
    }
}
