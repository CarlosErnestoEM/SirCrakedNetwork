package com.pollogamer.auth;

import fr.xephi.authme.api.API;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AuthMeBridgeListener implements Listener {

    Principal plugin;

    public static enum EnumLabyModFeature {
        FOOD, GUI, NICK, BLOCKBUILD, CHAT, EXTRAS, ANIMATIONS, POTIONS, ARMOR, DAMAGEINDICATOR, MINIMAP_RADAR;
    }

    public AuthMeBridgeListener(Principal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        HashMap<EnumLabyModFeature, Boolean> list = new HashMap();
        if (false) {
            list.put(EnumLabyModFeature.FOOD, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.GUI, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.NICK, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.BLOCKBUILD, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.CHAT, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.EXTRAS, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.ANIMATIONS, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.POTIONS, Boolean.valueOf(false));
        }
        if (false) {
            list.put(EnumLabyModFeature.ARMOR, Boolean.valueOf(false));
        }
        if (true) {
            list.put(EnumLabyModFeature.DAMAGEINDICATOR, Boolean.valueOf(false));
        }
        if (true) {
            list.put(EnumLabyModFeature.MINIMAP_RADAR, Boolean.valueOf(false));
        }
        v1_8_R3.setLabyModFeature(e.getPlayer(), list);
    }


    @EventHandler
    public void onAuthMeLogin(LoginEvent event) {
        if (!event.isLogin()) {
            return;
        }
        playerLogin(event.getPlayer());
    }

    @EventHandler
    public void onAuthMeSession(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
            public void run() {
                if (API.isAuthenticated(player)) {
                    AuthMeBridgeListener.this.playerLogin(player);
                }
            }
        }, 10L);
    }

    public void playerLogin(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("PlayerLogin");
            out.writeUTF(player.getName());
            new PluginMessageTask(this.plugin, player, b).runTaskAsynchronously(this.plugin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
