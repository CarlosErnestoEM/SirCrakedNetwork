package com.pollogamer.sircrakedffa.manager;


import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {

    private static ItemStackBuilder modos;

    public PlayerManager() {
        modos = new ItemStackBuilder(Material.PAPER).setName("§a§lModo de juego");
    }

    public static void setCleanPlayer(Player player, GameMode gamemode) {
        player.setGameMode(gamemode);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setExp(0.0f);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().clear();
    }

    public static void setbuilduhckit(Player player) {
        ItemStackBuilder casco = new ItemStackBuilder(Material.DIAMOND_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder peto = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder pantalones = new ItemStackBuilder(Material.DIAMOND_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder botas = new ItemStackBuilder(Material.DIAMOND_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder espada = new ItemStackBuilder(Material.DIAMOND_SWORD).addEnchantmentToStack(Enchantment.DAMAGE_ALL, 3);
        ItemStackBuilder arco = new ItemStackBuilder(Material.BOW).addEnchantmentToStack(Enchantment.ARROW_DAMAGE, 4);
        ItemStackBuilder caña = new ItemStackBuilder(Material.FISHING_ROD);
        ItemStackBuilder goldenhead = new ItemStackBuilder(Material.GOLDEN_APPLE).setName("§b§lGolden Head").setStackAmount(3);
        ItemStackBuilder manzana = new ItemStackBuilder(Material.GOLDEN_APPLE).setStackAmount(6);
        ItemStackBuilder mechero = new ItemStackBuilder(Material.FLINT_AND_STEEL);
        ItemStackBuilder flechas = new ItemStackBuilder(Material.ARROW).setStackAmount(64);
        ItemStackBuilder madera = new ItemStackBuilder(Material.WOOD).setStackAmount(64);
        ItemStackBuilder piedra = new ItemStackBuilder(Material.COBBLESTONE).setStackAmount(64);

        player.getInventory().clear();
        player.getInventory().setItem(0, espada);
        player.getInventory().setItem(1, caña);
        player.getInventory().setItem(2, arco);
        player.getInventory().setItem(5, manzana);
        player.getInventory().setItem(6, goldenhead);
        player.getInventory().setItem(7, mechero);
        player.getInventory().setItem(9, flechas);
        player.getInventory().setItem(8, modos);
        player.getInventory().setHelmet(casco);
        player.getInventory().setChestplate(peto);
        player.getInventory().setLeggings(pantalones);
        player.getInventory().setBoots(botas);
        player.getInventory().addItem(madera);
        player.getInventory().addItem(piedra);
    }

    public static void setkitcombo(final Player p) {
        p.getInventory().clear();
        ItemStackBuilder casco = new ItemStackBuilder(Material.DIAMOND_HELMET).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder peto = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder pantalones = new ItemStackBuilder(Material.DIAMOND_LEGGINGS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder botas = new ItemStackBuilder(Material.DIAMOND_BOOTS).addEnchantmentToStack(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStackBuilder espada = new ItemStackBuilder(Material.DIAMOND_SWORD).addEnchantmentToStack(Enchantment.DAMAGE_ALL, 3);
        ItemStackBuilder manzanas = new ItemStackBuilder(Material.GOLDEN_APPLE).setStackData((short) 1).setStackAmount(64);

        p.getInventory().setHelmet(casco);
        p.getInventory().setLeggings(pantalones);
        p.getInventory().setBoots(botas);
        p.getInventory().setChestplate(peto);
        p.getInventory().addItem(espada);
        p.getInventory().addItem(manzanas);
        p.getInventory().setItem(8, modos);
    }

    public static void setkitffadefault(final Player p) {
        ItemStack estrella = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = estrella.getItemMeta();
        meta.setDisplayName("§a§lSeleccionador de kits");
        estrella.setItemMeta(meta);
        ItemStack papel = new ItemStack(Material.PAPER);
        ItemMeta meta2 = papel.getItemMeta();
        meta2.setDisplayName("§a§lModo de juego");
        papel.setItemMeta(meta2);
        papel.setItemMeta(meta2);
        if (p.hasPermission("sircraked.kitvip")) {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
            p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(9, new ItemStack(Material.ARROW, 15));
            p.getInventory().setItem(7, estrella);
            p.getInventory().setItem(8, papel);
            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        } else {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
            p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(9, new ItemStack(Material.ARROW, 15));
            p.getInventory().setItem(8, papel);
            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        }
    }

    public static void setkitffavip(final Player p) {
        final ItemStack papel = new ItemStack(Material.PAPER);
        final ItemMeta meta1 = papel.getItemMeta();
        meta1.setDisplayName("§a§lModo de juego");
        papel.setItemMeta(meta1);
        final ItemStack estrella = new ItemStack(Material.NETHER_STAR);
        final ItemMeta meta2 = estrella.getItemMeta();
        meta2.setDisplayName("§a§lSeleccionador de kits");
        estrella.setItemMeta(meta2);
        if (p.hasPermission("sircraked.kitvip")) {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
            p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(7, estrella);
            p.getInventory().setItem(8, papel);
            p.getInventory().setItem(10, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(9, new ItemStack(Material.ARROW, 32));
            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
            p.closeInventory();
            p.sendMessage(Lang.prefix + "Has seleccionado el kit VIP");
        } else {
            p.sendMessage("§4§lSir§1§lCraked §7§l» §cNecesitas el rango VIP");
            p.closeInventory();
        }
    }

    public static void setkitffavip2(final Player p) {
        ItemStack papel = new ItemStack(Material.PAPER);
        ItemMeta meta1 = papel.getItemMeta();
        meta1.setDisplayName("§a§lModo de juego");
        papel.setItemMeta(meta1);
        ItemStack estrella = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta2 = estrella.getItemMeta();
        meta2.setDisplayName("§a§lSeleccionador de kits");
        estrella.setItemMeta(meta2);
        if (p.hasPermission("sircraked.kitvip+")) {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
            p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(7, estrella);
            p.getInventory().setItem(8, papel);
            p.getInventory().setItem(10, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(11, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            p.closeInventory();
        } else {
            p.sendMessage("§4§lSir§1§lCraked §7§l» §cNecesitas el rango VIP+");
            p.closeInventory();
        }
    }

    public static void setkitffasir(final Player p) {
        final ItemStack papel = new ItemStack(Material.PAPER);
        final ItemMeta meta1 = papel.getItemMeta();
        meta1.setDisplayName("§a§lModo de juego");
        papel.setItemMeta(meta1);
        final ItemStack estrella = new ItemStack(Material.NETHER_STAR);
        final ItemMeta meta2 = estrella.getItemMeta();
        meta2.setDisplayName("§a§lSeleccionador de kits");
        estrella.setItemMeta(meta2);
        if (p.hasPermission("sircraked.kitsir")) {
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
            p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
            p.getInventory().addItem(new ItemStack(Material.BOW));
            p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(7, estrella);
            p.getInventory().setItem(8, papel);
            p.getInventory().setItem(10, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(11, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(12, new ItemStack(Material.FLINT_AND_STEEL));
            p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
            p.closeInventory();
        } else {
            p.sendMessage("§4§lSir§1§lCraked §7§l» §cNecesitas el rango SIR");
            p.closeInventory();
        }
    }
}
