package com.minebone.gui.inventory.page;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SuccessPage<PluginType extends JavaPlugin> extends GUIPage {

    public SuccessPage(PluginType plugin, Player player) {
        super(plugin, player, "Completado!", 54);
        build();
    }

    public SuccessPage(PluginType plugin, Player player, boolean b) {
        super(plugin, player, "Completado!", 54, true);
        build();
    }

    public void buildPage() {
        ItemStack confirm = new ItemStackBuilder().setMaterial(Material.EMERALD_BLOCK).setName(ChatColor.GREEN + "Completado!");
        for (int i = 0; i < 54; i++) {
            addButton(new PlaceHolder(confirm), i);
        }

    }

    public void onInventoryCloseOverride() {
        onCloseInventory();
    }

    public void destroy() {
    }

    public void onCloseInventory() {
    }

}
