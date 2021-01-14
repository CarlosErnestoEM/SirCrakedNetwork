package com.pollogamer.wrapper.commands;

import com.pollogamer.wrapper.Main;

public class CMDHelp extends WrapperCommand {

    public CMDHelp() {
        super("help", new String[]{"h"}, "Comando de ayuda", "Usage 'help [Command]'");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            WrapperCommand wrapperCommand = Main.getMain().getCommandManager().getCommand(args[1]);
            if (wrapperCommand != null) {
                format(wrapperCommand.getCommandName(), wrapperCommand.getDescription());
                format(wrapperCommand.getCommandName(), wrapperCommand.getUsage());
            } else {
                format("ERROR", "The command " + args[1] + " not exist!");
            }
        } else {
            format("COMMANDS", "See the commands");
            for (WrapperCommand wrapperCommand : Main.getMain().getCommandManager().commands) {
                format(wrapperCommand.getCommandName(), wrapperCommand.getDescription());
            }
            format("COMMANDS", "All commands are showed!");
            format("COMMANDS", "You can usage help <Command Name>");
        }
    }
}
