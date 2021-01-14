package com.pollogamer.auth;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.pollogamer.sircrakedserver.utils.ScoreboardAPI;
import com.pollogamer.sircrakedserver.utils.ScoreboardIP;
import com.pollogamer.sircrakedserver.utils.Scroller;
import fr.xephi.authme.api.API;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

public class Principal extends JavaPlugin implements Listener, PluginMessageListener {

    private static Principal plugin;
    private static final String INIT_CHANNEL_NAME = "WDL|INIT";
    String incomingChannel = "BAuthMeBridge";
    String outgoingChannel = "AuthMeBridge";

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getPluginManager().registerEvents(this, this);
        new Utils(this);
        getCommand("authserver").setExecutor(new CMD());
        getServer().getMessenger().registerIncomingPluginChannel(this, "WDL|INIT", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "WDL|CONTROL");
        getServer().getPluginManager().registerEvents(new AuthMeBridgeListener(this), this);
        getServer().getMessenger().registerIncomingPluginChannel(this, this.incomingChannel, this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, this.outgoingChannel);
    }

    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|INIT");
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "WDL|CONTROL");
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord");
    }

    public void SendToLobby(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("Hub-1");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    @EventHandler
    public void onLogin(LoginEvent e) {
        e.getPlayer().sendMessage("");
        e.getPlayer().sendMessage("§a§lCUENTA §7» §aPara entrar a la lobby avanza!");
        e.getPlayer().sendMessage("");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat("§a%1$s§f: §f%2$s");
    }

    @EventHandler
    public void Join(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (API.isRegistered(p.getName())) {
            p.sendMessage("§a§lCUENTA §7» §FPorfavor logueate usando /login <Contraseña>");
        } else {
            p.sendMessage("§a§lCUENTA §7» §FPorfavor registrate usando /register <Contraseña> <Contraseña>");
        }
        for (Player obj : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(obj);
            obj.hidePlayer(p);
        }
        Utils.setCleanPlayer(p);
        event.setJoinMessage(null);
        p.teleport(Utils.spawn);
        ScoreboardAPI board = new ScoreboardAPI("&4&lSir&1&lCraked &a&lAuth");
        Scroller login = new Scroller("  §a/login <Contraseña>      ", 18, 0, '§');
        Scroller reg = new Scroller("  §a/register <Contraseña> <Contraseña>      ", 18, 0, '§');
        Scroller cp = new Scroller("  §a/changepassword <Vieja> <Nueva>     ", 18, 0, '§');
        board.add("&2", 10);
        board.add("Logueate con:", 9);
        board.add(login.next(), 8);
        board.add("&5", 7);
        board.add("Registrate con:", 6);
        board.add(reg.next(), 5);
        board.add("&1", 4);
        board.add("Cambia contraseña con:", 3);
        board.add(cp.next(), 2);
        board.add("&1&1", 1);
        board.add("&eplay.sircraked.com", 0);
        board.update();
        board.send(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnline()) {
                    board.add(login.next(), 8);
                    board.add(reg.next(), 5);
                    board.add(cp.next(), 2);
                    board.update();
                    board.send(p);
                } else {
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(Principal.plugin, 0, 2);
        new ScoreboardIP(board, p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (com.pollogamer.sircrakedserver.utils.Utils.moved(event)) {
            Player p = event.getPlayer();
            if (!API.isAuthenticated(p)) return;
            SendToLobby(p);
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void Lluvia(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void NivelDeHambre(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void Caida(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void RomperBloque(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!p.isOp()) {
            event.setCancelled(true);
            p.sendMessage("§4§lSir§1§lCraked §7§l» §cNo puedes romper eso");
        }
    }

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.getDrops().clear();
    }

    public void onPluginMessageReceived(String channel, Player p, byte[] data) {
        if (channel.equals(this.INIT_CHANNEL_NAME)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + p.getName() + " No robes mapas xd :v");
        } else if (channel.equals(this.incomingChannel)) {
            ByteArrayDataInput in = ByteStreams.newDataInput(data);
            String subchannel = in.readUTF();
            if (subchannel.equals("AutoLogin")) {
                Player player = Bukkit.getPlayer(in.readUTF());
                if (player != null) {
                    API.forceLogin(player);
                }
            }
        }
    }

    public static Principal getPlugin() {
        return plugin;
    }
}
