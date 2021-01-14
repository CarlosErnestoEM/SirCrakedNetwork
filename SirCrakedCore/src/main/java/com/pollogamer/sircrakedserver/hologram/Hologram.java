package com.pollogamer.sircrakedserver.hologram;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private String hologramName;
    private Location location;
    private List<String> lines = new ArrayList<>();
    private List<Integer> entityid = new ArrayList<>();
    private boolean save = false;

    public Hologram(String hologramName, Location location, List<String> lines, boolean save) {
        this.hologramName = hologramName;
        this.location = location;
        this.lines = lines;
        this.save = save;
        build();
    }

    private void build() {
        for (String s : lines) {
            spawnEntity(s);
        }
    }

    public void addLine(String s) {
        lines.add(ChatColor.translateAlternateColorCodes('&', s));
    }

    private void refresh() {

    }

    public void removeHologram() {
        for (Entity entity : location.getWorld().getEntities()) {
            if (entityid.contains(entity.getEntityId())) {
                entity.remove();
            }
        }
    }

    private int spawnEntity(String name) {
        ArmorStand a = (ArmorStand) location.getWorld().spawnEntity(location.add(0, -0.4, 0), EntityType.ARMOR_STAND);
        a.setVisible(false);
        a.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        a.setCustomNameVisible(true);
        a.setGravity(false);
        entityid.add(a.getEntityId());
        return a.getEntityId();
    }

    public String getHologramName() {
        return hologramName;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getLines() {
        return lines;
    }

    public boolean isSave() {
        return save;
    }
}
