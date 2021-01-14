package com.pollogamer.uhc.scenarios;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhc.Principal;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class AbstractScenerio implements Listener {

    private String name;
    private ItemStackBuilder item;
    public boolean enabled = false;

    public AbstractScenerio(String name, ItemStackBuilder item) {
        Bukkit.getPluginManager().registerEvents(this, Principal.getPlugin());
    }
}
