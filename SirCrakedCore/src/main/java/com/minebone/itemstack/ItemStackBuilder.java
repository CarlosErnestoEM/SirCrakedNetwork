package com.minebone.itemstack;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.*;

public class ItemStackBuilder extends ItemStack {

    public ItemStackBuilder() {
        this(Material.QUARTZ_BLOCK);
    }

    public ItemStackBuilder(Material material) {
        super(material);
        setLore(new ArrayList<>());
    }

    public ItemStackBuilder(ItemStack itemStack) {
        setData(itemStack.getData());
        setStackData(itemStack.getDurability());
        setAmount(itemStack.getAmount());
        setType(itemStack.getType());
        setItemMeta(itemStack.getItemMeta());
    }

    public ItemStackBuilder(ItemStack itemStack, Object n) {
        setData(itemStack.getData());
        setStackData(itemStack.getDurability());
        setAmount(itemStack.getAmount());
        setType(itemStack.getType());
    }

    public ItemStackBuilder setMaterial(Material material) {
        setType(material);
        return this;
    }

    public ItemStackBuilder changeAmount(int change) {
        setAmount(getAmount() + change);
        return this;
    }

    public ItemStackBuilder setStackAmount(int amount) {
        setAmount(amount);
        return this;
    }

    public ItemStackBuilder setStackData(short data) {
        setDurability(data);
        return this;
    }

    public ItemStackBuilder setStackData(MaterialData data) {
        setData(data);
        return this;
    }

    public ItemStackBuilder setEnchantments(HashMap<Enchantment, Integer> enchantments) {
        getEnchantments().keySet().forEach(this::removeEnchantment);
        addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemStackBuilder addEnchantmentToStack(Enchantment enchantment, int level) {
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        if (name == null) {
            return this;
        }

        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(name.equals("") ? " " : format(name));
        setItemMeta(itemMeta);
        return this;
    }

    public void setData(MaterialData data) {
        Field dataField;
        try {
            dataField = ItemStack.class.getDeclaredField("data");
            dataField.setAccessible(true);
            dataField.set(this, data);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ItemStackBuilder setEnchantedBook(Enchantment ench, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) getItemMeta();
        meta.addEnchant(ench, level, true);
        return this;
    }

    public ItemStackBuilder setColor(int red, int green, int blue) {
        return setColor(Color.fromRGB(red, green, blue));
    }

    public ItemStackBuilder setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) getItemMeta();
        leatherArmorMeta.setColor(color);
        setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemStackBuilder createBackItem() {
        setMaterial(Material.ARROW);
        setName(ChatColor.RED + "Back");
        return this;
    }

    public ItemStackBuilder createTogglableItem(boolean enabled) {
        if (enabled) {
            return createEnabledItem();
        }

        return createDisabledItem();
    }

    public ItemStackBuilder createEnabledItem() {
        setMaterial(Material.INK_SACK);
        setStackData((short) 10);
        setName(ChatColor.GREEN + "enabled");
        return this;
    }

    public ItemStackBuilder createDisabledItem() {
        setMaterial(Material.INK_SACK);
        setStackData((short) 8);
        setName(ChatColor.GREEN + "disabled");
        return this;
    }

    public ItemStackBuilder addBlankLore() {
        addLore(" ");
        return this;
    }

    public ItemStackBuilder addLineLore() {
        return addLineLore(20);
    }

    public ItemStackBuilder addLineLore(int length) {
        addLore("&8&m&l" + Strings.repeat("-", length));
        return this;
    }

    public ItemStackBuilder setSkullOwner(String owner) {
        setMaterial(Material.SKULL_ITEM);
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(owner);
        setStackData((short) 3);
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder createPlayerHead() {
        setMaterial(Material.SKULL_ITEM);
        setStackData((short) 3);
        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta itemMeta = getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null)
            original = new ArrayList<>();
        Collections.addAll(original, format(lore));
        itemMeta.setLore(original);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta itemMeta = getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null)
            original = new ArrayList<>();
        original.addAll(format(lore));
        itemMeta.setLore(original);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(format(Lists.newArrayList(lore)));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(format(lore));
        setItemMeta(itemMeta);
        return this;
    }

    public String format(String string) {
        return (string == null ? null : string.replace("&", "§"));
    }

    public String[] format(String[] strings) {
        return format(Arrays.asList(strings)).toArray(new String[strings.length]);
    }

    public List<String> format(List<String> strings) {
        List<String> collection = new ArrayList<>();
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                continue;
            }
            collection.add(format(string));
        }
        return collection;
    }
}