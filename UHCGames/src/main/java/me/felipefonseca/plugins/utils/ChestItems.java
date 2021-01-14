/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.EnchantmentStorageMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.felipefonseca.plugins.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class ChestItems {
    public static final ArrayList<ItemStack> ITEMS = new ArrayList();
    private static final Random random = new Random();

    public static void initItems() {
        ITEMS.clear();
        ChestItems.addArmas();
        ChestItems.addArmor();
        ChestItems.addComida();
        ChestItems.addOtros();
    }

    private static void addArmor() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HELMET);
        ItemStack itemStack2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack itemStack3 = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack itemStack4 = new ItemStack(Material.DIAMOND_BOOTS);
        ITEMS.add(itemStack);
        ITEMS.add(itemStack2);
        ITEMS.add(itemStack3);
        ITEMS.add(itemStack4);
    }

    private static void addArmas() {
        ItemStack itemStack = new ItemStack(Material.IRON_SWORD);
        ItemStack itemStack2 = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack itemStack3 = new ItemStack(Material.BOW);
        ItemStack itemStack4 = new ItemStack(Material.FISHING_ROD);
        ItemStack itemStack5 = new ItemStack(Material.ARROW, 3);
        ITEMS.add(itemStack);
        ITEMS.add(itemStack2);
        ITEMS.add(itemStack3);
        ITEMS.add(itemStack4);
        ITEMS.add(itemStack5);
    }

    private static void addComida() {
        ItemStack itemStack = new ItemStack(Material.COOKED_BEEF, 3);
        ItemStack itemStack2 = new ItemStack(Material.GOLDEN_APPLE, 1);
        ITEMS.add(itemStack);
        ITEMS.add(itemStack2);
    }

    private static void addOtros() {
        ItemStack itemStack = new ItemStack(Material.WOOD, 4);
        ItemStack itemStack2 = new ItemStack(Material.COBBLESTONE, 4);
        ItemStack itemStack3 = new ItemStack(Material.EXP_BOTTLE, 6);
        ItemStack itemStack4 = new ItemStack(Material.INK_SACK, 3, (short) 4);
        ItemStack itemStack5 = new ItemBuilder(Material.LAVA_BUCKET).setAmount(1).build();
        ItemStack itemStack6 = new ItemBuilder(Material.WATER_BUCKET).setAmount(1).build();
        ItemStack itemStack7 = new ItemBuilder(Material.GOLD_INGOT).setAmount(4).build();
        ItemStack itemStack8 = new ItemBuilder(Material.DIAMOND).setAmount(2).build();
        ItemStack itemStack9 = new ItemStack(Material.APPLE, 1);
        ITEMS.add(itemStack);
        ITEMS.add(itemStack2);
        ITEMS.add(itemStack3);
        ITEMS.add(itemStack4);
        ITEMS.add(itemStack5);
        ITEMS.add(itemStack6);
        ITEMS.add(itemStack7);
        ITEMS.add(itemStack9);
        ITEMS.add(itemStack8);
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.DAMAGE_ALL, 1));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.ARROW_DAMAGE, 1));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.ARROW_FIRE, 1));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.ARROW_KNOCKBACK, 1));
        ITEMS.add(ChestItems.getCustomEnchantedBook(Enchantment.PROTECTION_PROJECTILE, 1));
    }

    public static ItemStack[] getRandomItems() {
        int n = 5;
        ItemStack[] arritemStack = new ItemStack[n];
        for (int i = 0; i < n; ++i) {
            int n2 = random.nextInt(ITEMS.size() - 1);
            arritemStack[i] = ITEMS.get(n2);
        }
        return arritemStack;
    }

    private static ItemStack getCustomEnchantedBook(Enchantment enchantment, int n) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(enchantment, n, true);
        itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
        return itemStack;
    }
}

