package com.pollogamer.sircrakedserver.sanciones.util;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BanRecord implements IRecord {

    // General ban info
    private int id;
    private String banned;
    private String bannedBy;
    private String reason;
    private long time;
    private long banExpiresOn;

    // Unban record details
    private boolean unbanned;
    private String unbannedBy;
    private long unbannedtime;

    // Server info
    private String server;

    public BanRecord() {
    }

    public ItemStackBuilder getItemStack() {
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder();
        itemStackBuilder.setName(ChatColor.GREEN + (!unbanned ? "Ban (Actual)" : "Ban (Pasado)"));
        itemStackBuilder.setMaterial(Material.PAPER);
        itemStackBuilder.setAmount(1);

        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.YELLOW + "Baneado: " + ChatColor.WHITE + banned);
        lore.add(ChatColor.YELLOW + "Baneado Por: " + ChatColor.WHITE + bannedBy);
        lore.add(ChatColor.YELLOW + "Razón: " + ChatColor.WHITE + reason);
        lore.add(ChatColor.YELLOW + "Tiempo: " + ChatColor.WHITE + convertToDate(time));
        if (unbanned) {
            lore.add(ChatColor.YELLOW + "Desbaneado Por: " + ChatColor.WHITE + unbannedBy);
            //lore.add(ChatColor.YELLOW + "Desmuteado En:" + ChatColor.WHITE + MineBoneCore.getMineBoneCore().getDifferenceFormat(unmutedtime));
        } else {
            lore.add(ChatColor.YELLOW + "Expira En: " + ChatColor.WHITE + (banExpiresOn == 0 ? "Nunca" : getDifferenceFormat(banExpiresOn)));
        }
        lore.add(ChatColor.YELLOW + "Servidor: " + ChatColor.WHITE + server);
        itemStackBuilder.setLore(lore);

        return itemStackBuilder;
    }

    public String convertToDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);

        return calendar.getTime().toString();
    }

    public String getDifferenceFormat(long timestamp) {
        return formatDifference(timestamp - (System.currentTimeMillis() / 1000L));
    }

    public String formatDifference(long time) {
        if (time == 0) {
            return "Nunca";
        }

        long day = java.util.concurrent.TimeUnit.SECONDS.toDays(time);
        long hours = java.util.concurrent.TimeUnit.SECONDS.toHours(time) - (day * 24);
        long minutes = java.util.concurrent.TimeUnit.SECONDS.toMinutes(time) - (java.util.concurrent.TimeUnit.SECONDS.toHours(time) * 60);
        long seconds = java.util.concurrent.TimeUnit.SECONDS.toSeconds(time) - (java.util.concurrent.TimeUnit.SECONDS.toMinutes(time) * 60);

        StringBuilder sb = new StringBuilder();

        if (day > 0) {
            sb.append(day).append(" ").append(day == 1 ? "dia" : "dias").append(" ");
        }

        if (hours > 0) {
            sb.append(hours).append(" ").append(hours == 1 ? "hora" : "horas").append(" ");
        }

        if (minutes > 0) {
            sb.append(minutes).append(" ").append(minutes == 1 ? "minuto" : "minutos").append(" ");
        }

        if (seconds > 0) {
            sb.append(seconds).append(" ").append(seconds == 1 ? "segundo" : "segundos");
        }

        String diff = sb.toString();

        return diff.isEmpty() ? "ahora" : diff;
    }

    public int getId() {
        return id;
    }

    public String getBanned() {
        return banned;
    }

    public String getBannedBy() {
        return bannedBy;
    }

    public String getReason() {
        return reason;
    }

    public long getTime() {
        return time;
    }

    public long getBanExpiresOn() {
        return banExpiresOn;
    }

    public boolean isUnbanned() {
        return unbanned;
    }

    public String getUnbannedBy() {
        return unbannedBy;
    }

    public long getUnbannedtime() {
        return unbannedtime;
    }

    public String getServer() {
        return server;
    }

    @Override
    public String getPunished() {
        return banned;
    }

    @Override
    public String getPunishedBy() {
        return bannedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public void setBannedBy(String bannedBy) {
        this.bannedBy = bannedBy;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setBanExpiresOn(long banExpiresOn) {
        this.banExpiresOn = banExpiresOn;
    }

    public void setUnbanned(boolean unbanned) {
        this.unbanned = unbanned;
    }

    public void setUnbannedBy(String unbannedBy) {
        this.unbannedBy = unbannedBy;
    }

    public void setUnbannedtime(long unbannedtime) {
        this.unbannedtime = unbannedtime;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
