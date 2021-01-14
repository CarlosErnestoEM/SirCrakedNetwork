package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.page.ConfirmationPage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfirmButton<PluginType extends JavaPlugin> extends SimpleButton<PluginType> {

    private ItemStack item1;
    private ItemStack item2;

    public ConfirmButton(ItemStack item, ItemStack item1, ItemStack item2) {
        super(item);
        this.item1 = item1;
        this.item2 = item2;
    }

    public ConfirmButton(ItemStack item, ItemStack item1, ItemStack item2, ButtonAction<PluginType> buttonAction) {
        super(item, buttonAction);
        this.item1 = item1;
        this.item2 = item2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void click(GUIPage page) {
        new ConfirmationPage<PluginType>((PluginType) page.getPlugin(), page.getPlayer(), item1, item2) {
            @Override
            public void onConfirm() {
                if (action == null) {
                    return;
                }

                action.onClick((PluginType) page.getPlugin(), page.getPlayer(), page);
            }

            @Override
            public void onCancel() {
                getPlayer().closeInventory();
            }
        };
    }
}
