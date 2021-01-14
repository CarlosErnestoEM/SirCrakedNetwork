package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import org.bukkit.inventory.ItemStack;

public class CloseButton implements GUIButton {

    private ItemStack item;

    public CloseButton(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void click(GUIPage page) {
        page.getPlayer().closeInventory();
    }

    public void destroy() {
        this.item = null;
    }

}
