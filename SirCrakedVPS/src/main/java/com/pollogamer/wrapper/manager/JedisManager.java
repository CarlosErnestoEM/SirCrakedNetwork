package com.pollogamer.wrapper.manager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisManager {

    private static JedisPool pool;
    private String ip;
    private int port;
    private String password;

    public JedisManager() {
        ip = "158.69.253.240";
        port = 6379;
        password = "";
        connectToRedis();
    }

    public void connectToRedis() {
        Jedis rsc = null;
        format("SYSTEM", "Connecting with Redis database...");
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(-1);
            config.setJmxEnabled(false);
            setPool(new JedisPool(config, ip, port, 0, password));
            rsc = getPool().getResource();
            rsc.clientSetname("Wrapper");
            format("SYSTEM", "Successfully connected to Redis database!");
        } catch (Exception e) {
            if (rsc != null) {
                // getPool().returnBrokenResource(rsc);
            }
            getPool().destroy();
            setPool(null);
            format("ERROR", "An error ocurred trying connecting the database");
        }
    }

    public void stop() {
        try {
            pool.destroy();
        } catch (Exception e) {
        }
        format("SYSTEM", "Connection is closed!");
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public static JedisPool getPool() {
        return pool;
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
