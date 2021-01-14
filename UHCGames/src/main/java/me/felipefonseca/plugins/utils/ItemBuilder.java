/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.ItemStack
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  net.minecraft.server.v1_8_R3.NBTTagCompound
 *  net.minecraft.server.v1_8_R3.NBTTagList
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.felipefonseca.plugins.utils;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int n) {
        this.itemStack.setAmount(n);
        return this;
    }

    public ItemBuilder setDisplayName(String string) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(string);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLores(List<String> list) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(list);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public /* varargs */ ItemBuilder setLores(String... arrstring) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : arrstring) {
            arrayList.add(string);
        }
        itemMeta.setLore(arrayList);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[]{itemFlag});
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public /* varargs */ ItemBuilder addItemFlag(ItemFlag... arritemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(arritemFlag);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int n, boolean bl) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, n, bl);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder glow() {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy((ItemStack) this.itemStack);
        NBTTagCompound nBTTagCompound = null;
        if (!itemStack.hasTag()) {
            nBTTagCompound = new NBTTagCompound();
            itemStack.setTag(nBTTagCompound);
        }
        if (nBTTagCompound == null) {
            nBTTagCompound = itemStack.getTag();
        }
        NBTTagList nBTTagList = new NBTTagList();
        nBTTagCompound.set("ench", (NBTBase) nBTTagList);
        itemStack.setTag(nBTTagCompound);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}

