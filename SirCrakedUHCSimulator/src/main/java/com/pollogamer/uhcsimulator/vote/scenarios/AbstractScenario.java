package com.pollogamer.uhcsimulator.vote.scenarios;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class AbstractScenario implements Listener {

    private String name;
    private int slot;
    private ItemStackBuilder item;
    public boolean enabled;
    private int votes;
    private String description;
    private String publicname;

    public AbstractScenario(String name, int slot, ItemStackBuilder item, String description, String publicname) {
        this.name = name;
        this.slot = slot;
        this.item = item;
        this.description = description;
        this.publicname = publicname;
        enabled = false;
        votes = 0;
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
    }

    public void addVote() {
        votes++;
    }

    public void addVotes(int i) {
        votes += i;
    }

    public void removeVote() {
        votes--;
    }

    public void removeVotes(int i) {
        votes -= i;
    }

    public void setVotes(int i) {
        votes = i;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStackBuilder getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicname() {
        return publicname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getVotes() {
        return votes;
    }
}
