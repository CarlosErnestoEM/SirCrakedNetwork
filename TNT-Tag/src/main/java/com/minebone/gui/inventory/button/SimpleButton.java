package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleButton<PluginType extends JavaPlugin> implements GUIButton {

    protected ItemStack item;
    protected ButtonAction<PluginType> action;

    public SimpleButton(ItemStack item) {
        this.item = item;
    }

    public SimpleButton(ItemStack item, ButtonAction<PluginType> buttonAction) {
        this.item = item;
        this.action = buttonAction;
    }

    public SimpleButton<PluginType> setAction(ButtonAction<PluginType> action) {
        this.action = action;
        return this;
    }


    public ItemStack getItem() {
        return item;
    }

    public void click(GUIPage page) {
        if (action == null) {
            return;
        }
        action.onClick((PluginType) page.getPlugin(), page.getPlayer(), page);
    }

    public void destroy() {
        this.action = null;
        this.item = null;
    }
}
