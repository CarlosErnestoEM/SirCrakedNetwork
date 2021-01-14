package com.pollogamer.sircrakedserver.sanciones.util;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum HistoryPage {

    WARNING(11, new ItemStackBuilder().setMaterial(Material.DISPENSER).setName(ChatColor.GREEN + "Advertencias")),
    MUTE(13, new ItemStackBuilder().setMaterial(Material.BOOK_AND_QUILL).setName(ChatColor.GREEN + "Muteos")),
    BAN(15, new ItemStackBuilder().setMaterial(Material.BARRIER).setName(ChatColor.GREEN + "Baneos"));

    private int slot;
    private ItemStack itemStack;

    HistoryPage(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
