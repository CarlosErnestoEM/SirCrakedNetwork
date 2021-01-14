package com.pollogamer.tntrun;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.manager.MySQLManager;
import com.pollogamer.sircrakedserver.sql.HikariSQLManager;
import com.pollogamer.tntrun.commands.CMDGame;
import com.pollogamer.tntrun.commands.CMDTNTRun;
import com.pollogamer.tntrun.extras.Lang;
import com.pollogamer.tntrun.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Principal extends JavaPlugin {

    public static Principal plugin;
    public static MySQLManager mySQLManager;
    public static HikariSQLManager hikariSQLManager;

    public void onEnable() {
        plugin = this;
        getCommand("game").setExecutor(new CMDGame());
        getCommand("tntrun").setExecutor(new CMDTNTRun());
        saveDefaultConfig();
        new Lang();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        setWorld(Bukkit.getWorld("world"));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        startMySQL();
    }

    public void setWorld(World w) {
        w.setTime(6000);
        w.setAutoSave(false);
    }

    public void startMySQL() {
        try {
            hikariSQLManager = new HikariSQLManager(SirCrakedCore.getCore());
            hikariSQLManager.initConfiguration();
            hikariSQLManager.getDataSource("minijuegos");
            mySQLManager = new MySQLManager(SirCrakedCore.getCore());
            mySQLManager.initConfiguration("minijuegos");
            mySQLManager.openConnection();
            mySQLManager.createTable("CREATE TABLE IF NOT EXISTS `tntgames` (`jugador` VARCHAR(20), `coins` INT)");
            getLogger().info("Conectado a la MySQL");
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().info("No se puedo conectar a la base de datos! :(");
        }
    }
}
