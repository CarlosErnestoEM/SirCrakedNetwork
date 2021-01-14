package com.pollogamer.proxy;

import com.pollogamer.proxy.antibot.AntibotManager;
import com.pollogamer.proxy.comandos.*;
import com.pollogamer.proxy.listener.BroadcastManager;
import com.pollogamer.proxy.listener.NoDirectConnectBungee;
import com.pollogamer.proxy.listener.ServerListener;
import com.pollogamer.proxy.listener.StaffChat;
import com.pollogamer.proxy.manager.ModuleManager;
import com.pollogamer.proxy.object.PacketManager;
import com.pollogamer.proxy.wrapper.WrapperConnector;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

public class Principal extends Plugin {

    public NoDirectConnectBungee noDirectConnectBungee;
    public boolean mantenimiento = false;
    public BroadcastManager broadcastManager;
    public static Configuration config;
    public static String prefixping = "§aPing §7» ";
    public static String prefix = "§4§lSir§1§lCraked §7» §a";
    private static Principal plugin;
    private WrapperConnector wrapperConnector;
    private PacketManager packetManager;
    private AntibotManager antibotManager;
    private ModuleManager moduleManager;

    public void onEnable() {
        plugin = this;
        initConfig();
        wrapperConnector = new WrapperConnector();
        packetManager = new PacketManager();
        antibotManager = new AntibotManager();
        this.broadcastManager = new BroadcastManager(plugin);
        this.noDirectConnectBungee = new NoDirectConnectBungee();
        moduleManager = new ModuleManager();
        moduleManager.onEnable();
        registerCommands();
        getProxy().getPluginManager().registerListener(this, new NoDirectConnectBungee());
        getProxy().getPluginManager().registerListener(this, new StaffChat());
        getProxy().getPluginManager().registerListener(this, new ServerListener(this));
    }

    public void onDisable() {
        wrapperConnector.disconnect();
    }

    public void initConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                Files.copy(getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
            }
            loadConfig();
        } catch (IOException localIOException) {
            System.out.println("Check your config.yml");
        }
    }

    public void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException ignored) {
        }
    }

    public void registerCommands() {
        registerCommand(new CMDReply());
        registerCommand(new CMDPing());
        registerCommand(new CMDFind());
        registerCommand(new CMDSend());
        registerCommand(new CMDServer());
        registerCommand(new CMDMSG());
        registerCommand(new CMDReport());
        registerCommand(new CMDSocialSpy());
        registerCommand(new CMDMSGBlock());
        registerCommand(new CMDHelpop());
        registerCommand(new CMDMantenimiento());
        registerCommand(new CMDReload());
        registerCommand(new CMDFFA());
        registerCommand(new CMDGlobal());
        registerCommand(new CMDDispatch());
    }

    public void registerCommand(Command command) {
        BungeeCord.getInstance().getPluginManager().registerCommand(this, command);
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public WrapperConnector getWrapperConnector() {
        return wrapperConnector;
    }

    public AntibotManager getAntibotManager() {
        return antibotManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static Principal getPlugin() {
        return plugin;
    }

    public String getPrefix() {
        return prefix;
    }

}
