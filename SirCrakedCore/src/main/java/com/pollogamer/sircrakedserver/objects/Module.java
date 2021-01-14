package com.pollogamer.sircrakedserver.objects;

import com.pollogamer.SirCrakedCore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Module {

    public abstract void onEnable();

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, SirCrakedCore.getCore());
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
