package com.pollogamer.tnttag;

import com.pollogamer.tnttag.cmd.CMDGame;
import com.pollogamer.tnttag.cmd.CMDTNTTag;
import com.pollogamer.tnttag.extras.Lang;
import com.pollogamer.tnttag.listener.PlayerListener;
import com.pollogamer.tnttag.manager.PlayerManager;
import com.pollogamer.tnttag.task.GameTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Principal extends JavaPlugin {

    public static Principal plugin;
    public static PlayerManager playerManager;
    //public static MySQLManager mySQLManager;
    //public static HikariSQLManager hikariSQLManager;

    public void onEnable() {
        plugin = this;
        //startMySQL();
        saveDefaultConfig();
        playerManager = new PlayerManager();
        getCommand("tnttag").setExecutor(new CMDTNTTag());
        getCommand("game").setExecutor(new CMDGame());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        new Lang(plugin);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void startMySQL() {
     /*   try {
            hikariSQLManager = new HikariSQLManager(SirCrakedCore.getCore());
            hikariSQLManager.initConfiguration();
            hikariSQLManager.getDataSource("minijuegos");
            mySQLManager = new MySQLManager(SirCrakedCore.getCore());
            mySQLManager.initConfiguration("minijuegos");
            mySQLManager.openConnection();
            mySQLManager.createTable("CREATE TABLE IF NOT EXISTS `tntgames` (`jugador` VARCHAR(20), `coins` INT)");
            getLogger().info("Conectado a la MySQL");
        }catch (Exception e){
            e.printStackTrace();
            getLogger().info("No se puedo conectar a la base de datos! :(");
        }*/
    }

    public static void setupGame() {
        Lang.forcestart = false;
        Lang.started = false;
        Lang.starting = false;
        GameTask.fase = 0;
        GameTask.winner = "No establecido";
        PlayerListener.players.clear();
        PlayerListener.spectators.clear();
        PlayerListener.tntplayers.clear();
        PlayerListener.coins.clear();
        System.out.println("Se ha empezado otro juego :v");
    }
}
