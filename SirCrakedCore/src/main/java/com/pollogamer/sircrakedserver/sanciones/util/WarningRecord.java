package com.pollogamer.sircrakedserver.sanciones.util;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WarningRecord implements IRecord {

    // General warn info
    private int id;
    private String warned;
    private String warnedBy;
    private String reason;
    private long time;

    // Server info
    private String server;

    public WarningRecord() {

    }

    public ItemStackBuilder getItemStack() {
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder();
        itemStackBuilder.setName(ChatColor.GREEN + "Advertencia");
        itemStackBuilder.setMaterial(Material.PAPER);
        itemStackBuilder.setAmount(1);

        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.YELLOW + "Baneado: " + ChatColor.WHITE + banned);
        lore.add(ChatColor.YELLOW + "Advertido Por: " + ChatColor.WHITE + warnedBy);
        lore.add(ChatColor.YELLOW + "Razón: " + ChatColor.WHITE + reason);
        lore.add(ChatColor.YELLOW + "Tiempo: " + ChatColor.WHITE + convertToDate(time));
        lore.add(ChatColor.YELLOW + "Servidor: " + ChatColor.WHITE + server);
        itemStackBuilder.setLore(lore);

        return itemStackBuilder;
    }

    public String convertToDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);

        return calendar.getTime().toString();
    }

    @Override
    public String getPunished() {
        return warned;
    }

    @Override
    public String getPunishedBy() {
        return warnedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWarned() {
        return warned;
    }

    public void setWarned(String warned) {
        this.warned = warned;
    }

    public String getWarnedBy() {
        return warnedBy;
    }

    public void setWarnedBy(String warnedBy) {
        this.warnedBy = warnedBy;
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

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
