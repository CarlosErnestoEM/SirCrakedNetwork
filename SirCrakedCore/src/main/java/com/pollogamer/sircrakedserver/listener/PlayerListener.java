package com.pollogamer.sircrakedserver.listener;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.comandos.CMDFreeze;
import com.pollogamer.sircrakedserver.objects.SirPlayer;
import com.pollogamer.sircrakedserver.troll.TrollManager;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.utils.MySQLCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class PlayerListener implements Listener {

    private String BungeeIP;
    private Boolean IPWL;
    public HashMap<String, Long> cooldowns = new HashMap<>();
    public static boolean chat = false;
    public static String chatmutedby = "No establecido";

    public PlayerListener() {
        this.BungeeIP = SirCrakedCore.getCore().getConfig().getString("BungeeIP");
        this.IPWL = SirCrakedCore.getCore().getConfig().getBoolean("IPWL");
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e) {
        if (!e.getRightClicked().isVisible()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat2(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer().hasPermission("sircraked.chat.bypass")) return;
        if (chat) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.prefix + "El chat se encuentra muteado por " + chatmutedby);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if (CMDFreeze.freeze.contains(event.getEntity().getName()) || CMDFreeze.freeze.contains(event.getDamager().getName()) || TrollManager.trolled.contains(event.getDamager()) || TrollManager.trolled.contains(event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onlogin(PlayerLoginEvent event) {
        if (!event.getPlayer().hasPermission("sircraked.joinfull")) {
            return;
        }
        if (event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
            event.allow();
        }
    }

    @EventHandler
    public boolean onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return true;
        }
        if (!e.getPlayer().hasPermission("sircraked.chat.bypass")) {
            int cooldown = 2;
            Player sender = e.getPlayer();
            if (this.cooldowns.containsKey(sender.getName())) {
                long secondsLeft = this.cooldowns.get(sender.getName()) / 1000L + cooldown - System.currentTimeMillis() / 1000L;
                if (secondsLeft > 0L) {
                    sender.sendMessage("§4§lSir§1§lCraked §7» §aNo hables tan rapido! Espera 2 segundos  para volver a hablar o compra VIP!");
                    e.setCancelled(true);
                    return true;
                }
            }
            this.cooldowns.put(sender.getName(), System.currentTimeMillis());
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        new MySQLCheck(p);
        SirPlayer.getPlayer(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (cooldowns.containsKey(player.getName())) {
            cooldowns.remove(player.getName());
        }
        SirPlayer.unregisterPlayer(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (IPWL) {
            if (!event.getRealAddress().getHostAddress().equals(BungeeIP)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4§lSir§1§lCraked §7» §aNo entre de otra IP puto");
            }
        }
    }
}
