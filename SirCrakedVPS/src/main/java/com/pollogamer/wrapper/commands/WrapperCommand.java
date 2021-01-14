package com.pollogamer.wrapper.commands;

public abstract class WrapperCommand {

    private String commandName;
    private String[] aliases;
    private String description;
    private String usage;

    public WrapperCommand(String commandName, String[] aliases, String description, String usage) {
        this.commandName = commandName;
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;
    }

    public abstract void execute(String[] args);

    public String getCommandName() {
        return commandName;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
