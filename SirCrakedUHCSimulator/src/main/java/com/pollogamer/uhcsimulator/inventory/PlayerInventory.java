package com.pollogamer.uhcsimulator.inventory;

import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerInventory {

    private List<ItemStack> helmet = new ArrayList<>();
    private List<ItemStack> chestplate = new ArrayList<>();
    private List<ItemStack> leggins = new ArrayList<>();
    private List<ItemStack> boots = new ArrayList<>();
    private static final Random r = new Random();

    public PlayerInventory() {
        addHelmets();
        addChestPlate();
        addLeggins();
        addBoots();
        new Kit();
    }

    public void setArmor(Player player) {
        player.getInventory().setHelmet(helmet.get(r.nextInt(helmet.size())));
        player.getInventory().setChestplate(chestplate.get(r.nextInt(chestplate.size())));
        player.getInventory().setLeggings(leggins.get(r.nextInt(leggins.size())));
        player.getInventory().setBoots(boots.get(r.nextInt(boots.size())));
    }

    public void setAllPlayer(Player player) {
        setArmor(player);
        player.getInventory().setContents(Kit.inventory.get(r.nextInt(Kit.inventory.size())));
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE, 1));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
        player.setLevel(r.nextInt(10));
        player.updateInventory();
    }

    public void addHelmets() {
        helmet.add(new ItemStackBuilder(Material.DIAMOND_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        helmet.add(new ItemStackBuilder(Material.DIAMOND_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        helmet.add(new ItemStackBuilder(Material.DIAMOND_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        helmet.add(new ItemStackBuilder(Material.IRON_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        helmet.add(new ItemStackBuilder(Material.IRON_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        helmet.add(new ItemStackBuilder(Material.IRON_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
    }

    public void addChestPlate() {
        chestplate.add(new ItemStackBuilder(Material.IRON_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        chestplate.add(new ItemStackBuilder(Material.IRON_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        chestplate.add(new ItemStackBuilder(Material.IRON_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        chestplate.add(new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        chestplate.add(new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
    }

    public void addLeggins() {
        leggins.add(new ItemStackBuilder(Material.DIAMOND_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        leggins.add(new ItemStackBuilder(Material.DIAMOND_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        leggins.add(new ItemStackBuilder(Material.DIAMOND_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        leggins.add(new ItemStackBuilder(Material.IRON_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        leggins.add(new ItemStackBuilder(Material.IRON_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        leggins.add(new ItemStackBuilder(Material.IRON_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
    }

    public void addBoots() {
        boots.add(new ItemStackBuilder(Material.DIAMOND_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        boots.add(new ItemStackBuilder(Material.DIAMOND_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        boots.add(new ItemStackBuilder(Material.DIAMOND_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        boots.add(new ItemStackBuilder(Material.IRON_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        boots.add(new ItemStackBuilder(Material.IRON_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        boots.add(new ItemStackBuilder(Material.IRON_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
    }


}
