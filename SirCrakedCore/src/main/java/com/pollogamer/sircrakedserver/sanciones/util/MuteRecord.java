package com.pollogamer.sircrakedserver.sanciones.util;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MuteRecord implements IRecord {

    // General mute info
    private int id;
    private String muted;
    private String mutedBy;
    private String reason;
    private long time;
    private long muteExpiresOn;

    // Unmute record details
    private boolean unmuted;
    private String unmutedBy;
    private long unmutedtime;

    // Server info
    private String server;

    public MuteRecord() {
    }

    public ItemStackBuilder getItemStack() {
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder();
        itemStackBuilder.setName(ChatColor.GREEN + (!unmuted ? "Mute (Actual)" : "Mute (Pasado)"));
        itemStackBuilder.setMaterial(Material.PAPER);
        itemStackBuilder.setAmount(1);

        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.YELLOW + "Muteado: " + ChatColor.WHITE + "");
        lore.add(ChatColor.YELLOW + "Muteado Por: " + ChatColor.WHITE + mutedBy);
        lore.add(ChatColor.YELLOW + "Razón: " + ChatColor.WHITE + reason);
        lore.add(ChatColor.YELLOW + "Tiempo: " + ChatColor.WHITE + convertToDate(time));
        if (unmuted) {
            lore.add(ChatColor.YELLOW + "Desmuteado Por: " + ChatColor.WHITE + unmutedBy);
            //lore.add(ChatColor.YELLOW + "Desmuteado En:" + ChatColor.WHITE + MineBoneCore.getMineBoneCore().getDifferenceFormat(unmutedtime));
        } else {
            lore.add(ChatColor.YELLOW + "Expira En: " + ChatColor.WHITE + (muteExpiresOn == 0 ? "Nunca" : getDifferenceFormat(muteExpiresOn)));
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

    @Override
    public String getPunished() {
        return muted;
    }

    @Override
    public String getPunishedBy() {
        return mutedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMuted() {
        return muted;
    }

    public void setMuted(String muted) {
        this.muted = muted;
    }

    public String getMutedBy() {
        return mutedBy;
    }

    public void setMutedBy(String mutedBy) {
        this.mutedBy = mutedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMuteExpiresOn() {
        return muteExpiresOn;
    }

    public void setMuteExpiresOn(long muteExpiresOn) {
        this.muteExpiresOn = muteExpiresOn;
    }

    public boolean isUnmuted() {
        return unmuted;
    }

    public void setUnmuted(boolean unmuted) {
        this.unmuted = unmuted;
    }

    public String getUnmutedBy() {
        return unmutedBy;
    }

    public void setUnmutedBy(String unmutedBy) {
        this.unmutedBy = unmutedBy;
    }

    public long getUnmutedtime() {
        return unmutedtime;
    }

    public void setUnmutedtime(long unmutedtime) {
        this.unmutedtime = unmutedtime;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
