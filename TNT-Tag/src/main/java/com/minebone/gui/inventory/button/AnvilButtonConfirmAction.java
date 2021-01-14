package com.minebone.gui.inventory.button;

import com.minebone.gui.anvil.AnvilClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface AnvilButtonConfirmAction<PluginType extends JavaPlugin> {

    void onConfirm(PluginType plugin, Player player, AnvilClickEvent event);

}
