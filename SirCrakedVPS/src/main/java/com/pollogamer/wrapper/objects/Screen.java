package com.pollogamer.wrapper.objects;

import com.pollogamer.wrapper.utils.BashUtils;

public class Screen {

    private String screenName;

    public Screen(String screenName) {
        this.screenName = screenName;
        createScreen();
    }


    public void createScreen() {
        BashUtils.executeBashCommand("screen -dmS " + screenName);
    }

    public void sendCommand(String command) {
        BashUtils.executeBashCommand("screen -S " + screenName + " -X stuff '" + command + "^M'");
    }

    public boolean startServer(String args) {
        return BashUtils.executeBashCommand("screen -S " + screenName + " -X stuff '" + args + "^M'");
    }

    public void stopServer() {
        BashUtils.executeBashCommand("stop");
    }

    public void deleteScreen() {
        BashUtils.executeBashCommand("screen -X -S " + screenName + " kill");
    }

}
