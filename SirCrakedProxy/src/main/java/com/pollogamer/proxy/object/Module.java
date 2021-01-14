package com.pollogamer.proxy.object;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

public abstract class Module {

    public abstract void onEnable();

    public void registerListener(Listener listener) {
        BungeeCord.getInstance().getPluginManager().registerListener(Principal.getPlugin(), listener);
    }

    public void registerCommand(Command command) {
        Principal.getPlugin().registerCommand(command);
    }

    public void log(String text) {
        BungeeCord.getInstance().getConsole().sendMessage(text);
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
