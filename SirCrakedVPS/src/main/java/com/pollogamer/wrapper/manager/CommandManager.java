package com.pollogamer.wrapper.manager;

import com.pollogamer.wrapper.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public List<WrapperCommand> commands = new ArrayList<>();

    public CommandManager() {
        registerCommand(new CMDHelp());
        registerCommand(new CMDStop());
        registerCommand(new CMDSendCommand());
        registerCommand(new CMDList());
        registerCommand(new CMDLag());
        registerCommand(new CMDCreateServer());
        registerCommand(new CMDMinigame());
        registerCommand(new CMDServer());
        registerCommand(new CMDGroup());
    }

    public void runCommand(String[] args) {
        WrapperCommand wrapperCommand = getCommand(args);
        if (wrapperCommand != null) {
            wrapperCommand.execute(args);
        } else {
            if (!args[0].equalsIgnoreCase("")) {
                format("ERROR", "Command " + args[0] + " not found! Please usage 'help' to view all commands");
            }
        }
    }

    public WrapperCommand getCommand(String[] args) {
        for (WrapperCommand command : commands) {
            if (args[0].equalsIgnoreCase(command.getCommandName())) {
                return command;
            } else {
                for (String s : command.getAliases()) {
                    if (s.equalsIgnoreCase(args[0])) {
                        return command;
                    }
                }
            }
        }
        return null;
    }

    public WrapperCommand getCommand(String args) {
        for (WrapperCommand command : commands) {
            if (args.equalsIgnoreCase(command.getCommandName())) {
                return command;
            } else {
                for (String s : command.getAliases()) {
                    if (s.equalsIgnoreCase(args)) {
                        return command;
                    }
                }
            }
        }
        return null;
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public void registerCommand(WrapperCommand wrapperCommand) {
        commands.add(wrapperCommand);
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
