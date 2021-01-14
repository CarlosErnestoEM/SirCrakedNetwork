package com.minebone.gui.inventory.page;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FailedPage<PluginType extends JavaPlugin> extends GUIPage {

    private String[] reason;

    public FailedPage(PluginType plugin, Player player, String... reason) {
        super(plugin, player, "Error!", 54);
        this.reason = reason;
        build();
    }

    public void buildPage() {
        ItemStack confirm = new ItemStackBuilder(Material.REDSTONE_BLOCK).setName(ChatColor.RED + "ERROR:").setLore(reason);
        for (int i = 0; i < 54; i++) {
            addButton(new PlaceHolder(confirm), i);
        }

    }

    public void destroy() {
        this.reason = null;
    }

}
