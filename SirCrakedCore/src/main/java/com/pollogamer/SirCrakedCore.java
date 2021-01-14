package com.pollogamer;

import com.pollogamer.sircrakedserver.cleangenerator.CleanroomChunkGenerator;
import com.pollogamer.sircrakedserver.csf.Engine;
import com.pollogamer.sircrakedserver.manager.*;
import com.pollogamer.sircrakedserver.sanciones.Punish;
import com.pollogamer.sircrakedserver.sql.HikariSQLManager;
import com.pollogamer.sircrakedserver.staffmode.StaffMode;
import com.pollogamer.sircrakedserver.troll.TrollManager;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.vanish.Vanish;
import com.pollogamer.sircrakedserver.wrapper.WrapperConnector;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;


public class SirCrakedCore extends JavaPlugin {

    private final HikariSQLManager hikariSQLManager;
    private final Punish punish;
    private static SirCrakedCore core;
    private JedisManager jedisManager;
    private WrapperConnector wrapperConnector;
    private PacketManager packetManager;
    private ModuleManager moduleManager;

    public SirCrakedCore() {
        SirCrakedCore.core = this;
        this.punish = new Punish(this);
        this.hikariSQLManager = new HikariSQLManager("sircraked");
    }

    public void onEnable() {
        startAll();
        initMySQL();
    }

    public void onDisable() {
        hikariSQLManager.getDataSource().close();
        wrapperConnector.disconnect();
    }

    public void startAll() {
        ConfigManager.loadConfig();
        new Lang();
        moduleManager = new ModuleManager();
        //jedisManager = new JedisManager();
        CommandManager.registerCMD(this);
        EventManager.registerevents();
        new Engine();
        new TrollManager();
        new StaffMode();
        new Vanish();
        packetManager = new PacketManager();
        wrapperConnector = new WrapperConnector();
    }

    public void initMySQL() {
        getLogger().info("Conectando a la MySQL....");
        hikariSQLManager.openConnection();
        hikariSQLManager.createTable("CREATE TABLE IF NOT EXISTS `usuarios` (`ID` int(11) unsigned NOT NULL auto_increment, `Realname` VARCHAR(20), `Username` VARCHAR(20), `firstJoin` VARCHAR(20), `coins` int(11), `level` DECIMAL(11), PRIMARY KEY (`ID`))");
    }

    public Punish getPunish() {
        return punish;
    }

    public HikariSQLManager getHikariSQLManager() {
        return hikariSQLManager;
    }

    public static SirCrakedCore getCore() {
        return core;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public WrapperConnector getWrapperConnector() {
        return wrapperConnector;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
        return new CleanroomChunkGenerator(id);
    }
}