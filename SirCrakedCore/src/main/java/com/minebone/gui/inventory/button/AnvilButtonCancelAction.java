package com.minebone.gui.inventory.button;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface AnvilButtonCancelAction<PluginType extends JavaPlugin> {

    void onCancel(PluginType plugin, Player player);

}
