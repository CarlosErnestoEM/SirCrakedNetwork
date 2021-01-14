package com.minebone.gui.inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public interface GUIButton<PluginType extends JavaPlugin> {

    void click(GUIPage<PluginType> page);

    void destroy();

    ItemStack getItem();

}
