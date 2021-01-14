package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.objects.McServer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisManager {

    private static JedisPool pool;
    private String servername = Bukkit.getServerName();
    private String ip;
    private int port;
    private String password;

    public JedisManager() {
        ip = "158.69.253.240";
        port = 6379;
        password = "nomamadaaa";
        connectToRedis();
    }

    public void connectToRedis() {
        Jedis rsc = null;
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(-1);
            config.setJmxEnabled(false);
            setPool(new JedisPool(config, ip, port, 0, password));
            rsc = getPool().getResource();
            rsc.clientSetname("Servidor-" + servername);
            startProcess();
        } catch (Exception e) {
            if (rsc != null) {
                getPool().returnBrokenResource(rsc);
            }
            getPool().destroy();
            setPool(null);
            e.printStackTrace();
        }
    }

    public void startProcess() {
        Jedis rsc = pool.getResource();
        Pipeline pipeline = rsc.pipelined();
        String key = "status." + Bukkit.getServerName();
        pipeline.set(key, putThisServer());
        pipeline.expire(key, 1);
        new BukkitRunnable() {
            public void run() {
                try {
                    pipeline.set(key, putThisServer());
                    pipeline.expire(key, 1);
                } catch (JedisConnectionException e) {
                    throw e;
                }
            }
        }.runTaskTimerAsynchronously(SirCrakedCore.getCore(), 0, 10);
    }

    public String putThisServer() {
        return new McServer().serializeGson();
    }

    public String getString(String servername) {
        return pool.getResource().get("status." + servername);
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public JedisPool getPool() {
        return pool;
    }
}
