package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.comandos.*;

public class CommandManager {

    public static void registerCMD(SirCrakedCore plugin) {
        CMDCoins coins = new CMDCoins();
        CMDYoutuber youtuber = new CMDYoutuber();
        plugin.getCommand("coins").setExecutor(coins);
        plugin.getCommand("addcoins").setExecutor(coins);
        plugin.getCommand("setcoins").setExecutor(coins);
        plugin.getCommand("removecoins").setExecutor(coins);
        plugin.getCommand("fly").setExecutor(new CMDFly());
        plugin.getCommand("anunciar").setExecutor(new CMDAnunciar());
        plugin.getCommand("youtuberset").setExecutor(youtuber);
        plugin.getCommand("youtuberquit").setExecutor(youtuber);
        plugin.getCommand("reinicio").setExecutor(new CMDReinicio());
        plugin.getCommand("s").setExecutor(new CMDSancionar(plugin));
        plugin.getCommand("invsee").setExecutor(new CMDInvsee());
        plugin.getCommand("holograma").setExecutor(new CMDHolograma());
        plugin.getCommand("chat").setExecutor(new CMDChat());
        plugin.getCommand("freeze").setExecutor(new CMDFreeze());
        plugin.getCommand("troll").setExecutor(new CMDTroll());
        plugin.getCommand("sc").setExecutor(new CMDSc());
    }
}
