package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.SirCrakedCore;

import java.sql.ResultSet;

public class PlayerInfo {

    public static int getCoins(String p) {
        int c = 0;
        try {
            ResultSet rs = SirCrakedCore.getCore().getHikariSQLManager().Query("SELECT `coins` FROM `usuarios` WHERE `username`='" + p.toLowerCase() + "'");
            while (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return c;
    }

    public static double getLevel(String p) {
        double c = 0;
        try {
            ResultSet rs = SirCrakedCore.getCore().getHikariSQLManager().Query("SELECT `level` FROM `usuarios` WHERE `username`='" + p.toLowerCase() + "'");
            while (rs.next()) {
                c = rs.getDouble(1);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return c;
    }

}
