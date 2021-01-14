package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.hologram.HoloListener;
import com.pollogamer.sircrakedserver.listener.PlayerListener;
import org.bukkit.Bukkit;

/**
 * Created by Carlos on 05/05/2017.
 */
public class EventManager {

    public static void registerevents() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), SirCrakedCore.getCore());
        Bukkit.getServer().getPluginManager().registerEvents(SirCrakedCore.getCore().getPunish(), SirCrakedCore.getCore());
        Bukkit.getServer().getPluginManager().registerEvents(new HoloListener(), SirCrakedCore.getCore());
    }
}
