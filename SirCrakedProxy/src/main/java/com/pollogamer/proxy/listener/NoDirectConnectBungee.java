package com.pollogamer.proxy.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class NoDirectConnectBungee implements Listener {

    public Cache<String, Byte> ips = CacheBuilder.newBuilder().maximumSize(200).expireAfterWrite(2, TimeUnit.MINUTES).build();
    boolean NoDirectConnect;

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        String ip = event.getConnection().getAddress().getAddress().getHostAddress();
        this.ips.put(ip, (byte) 0);
    }

    @EventHandler
    public void onConnect(LoginEvent event) {
        String ip = event.getConnection().getAddress().getAddress().getHostAddress();
        if (this.ips.getIfPresent(ip) == null) {
            if (!this.ips.asMap().containsKey(ip)) {
                event.setCancelled(true);
                event.setCancelReason("§4§LSIR§1§lCRAKED\n\n§fAgrega el servidor a tu lista de servidores\n§fy actualiza como minimo 1 vez para\npoder entrar\n\n§bplay.sircraked.com");
            } else {
                this.ips.invalidate(ip);
            }
        }
    }
}