package com.minebone.gui.inventory.button;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IncrementingButton implements GUIButton {

    private final ItemStack item;
    private int amount;
    private IncrementingButtonAction action;

    public IncrementingButton(ItemStackBuilder item, int amount) {
        this.item = item.setStackAmount(Math.abs(amount));
    }

    public IncrementingButton(ItemStackBuilder item, int amount, IncrementingButtonAction buttonAction) {
        this.item = item.setStackAmount(Math.abs(amount));
        this.action = buttonAction;
    }

    public IncrementingButton(int amount, IncrementingButtonAction buttonAction) {
        this.item = new ItemStackBuilder().setMaterial(Material.STAINED_GLASS_PANE).setStackAmount(Math.abs(amount)).setStackData((short) (amount < 0 ? 14 : 5)).setName((amount < 0 ? ChatColor.RED : ChatColor.GREEN).toString() + amount);
        this.amount = amount;
        this.action = buttonAction;
    }

    public IncrementingButton setAction(IncrementingButtonAction action) {
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

        action.onClick(page.getPlugin(), page.getPlayer(), page, amount);
    }

    public void destroy() {
    }
}
