package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import org.bukkit.inventory.ItemStack;

public class PlaceHolder implements GUIButton {

    private ItemStack item;

    public PlaceHolder(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void click(GUIPage page) {
    }

    public void destroy() {
        this.item = null;
    }

}
