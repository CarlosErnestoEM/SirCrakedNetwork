package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.objects.MinigameServer;

public class CMDMinigame extends WrapperCommand {

    public CMDMinigame() {
        super("minigame", new String[]{"mg"}, "You can stop a minigame", "Usage 'minigame <MiniGame-Port|List> <Stop|Working on more>'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 3) {
            String minigameid = args[1];
            MinigameServer minigameServer = MinigameServer.getMiniGame(minigameid);
            if (minigameServer != null) {
                String action = args[2];
                switch (action.toLowerCase()) {
                    case "stop":
                        minigameServer.end();
                        break;
                    default:
                        format("ERROR", getUsage());
                        break;
                }
            } else {
                format("ERROR", "MiniGame " + minigameid + " doesnt exist!");
            }
        } else if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "list":
                    if (MinigameServer.getServers().size() == 0) {
                        format("ERROR", "Any minigame server is running! :( Create one using 'createserver'");
                    } else {
                        format("MINIGAME", "List of servers running...");
                        log(getServers());
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

    public String getServers() {
        String servers = "";
        for (int i = 0; i < MinigameServer.getServers().size(); i++) {
            servers += MinigameServer.getServers().get(i).getSvType() + "-" + MinigameServer.getServers().get(i).getPort();
            if ((i + 1) < MinigameServer.getServers().size()) {
                servers += ", ";
            }
        }
        return servers;
    }
}
