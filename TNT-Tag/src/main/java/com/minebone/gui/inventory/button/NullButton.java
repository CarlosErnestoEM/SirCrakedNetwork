package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import org.bukkit.inventory.ItemStack;

public class NullButton implements GUIButton {

    @Override
    public void click(GUIPage page) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public ItemStack getItem() {
        return null;
    }
}
