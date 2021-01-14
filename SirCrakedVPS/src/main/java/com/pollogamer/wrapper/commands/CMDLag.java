package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.utils.SystemInfo;

public class CMDLag extends WrapperCommand {

    public CMDLag() {
        super("lag", new String[]{"system", "systeminfo", "infocpu"}, "See the system information", "Usage 'lag'");
    }

    @Override
    public void execute(String[] args) {
        format(getCommandName(), "System Info");
        log(new SystemInfo().Info());
    }
}
