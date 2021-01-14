package com.pollogamer.sircrakedserver.utils;

import org.bukkit.Material;

public class Materials {

    public static boolean isHelmet(Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case IRON_HELMET:
            case GOLD_HELMET:
            case DIAMOND_HELMET:
            case CHAINMAIL_HELMET: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isBoots(Material material) {
        switch (material) {
            case LEATHER_BOOTS:
            case IRON_BOOTS:
            case GOLD_BOOTS:
            case DIAMOND_BOOTS:
            case CHAINMAIL_BOOTS: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isChestplate(Material material) {
        switch (material) {
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isLeggings(Material material) {
        switch (material) {
            case LEATHER_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLD_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case CHAINMAIL_LEGGINGS: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isBarding(Material material) {
        switch (material) {
            case IRON_BARDING:
            case GOLD_BARDING:
            case DIAMOND_BARDING: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isArmor(Material material) {
        return isHelmet(material) || isChestplate(material) || isLeggings(material) || isBoots(material) || isBarding(material);
    }

    public static boolean isWeapon(Material material) {
        return material == Material.BOW || isSword(material);
    }

    public static boolean isTool(Material material) {
        if (isSpade(material) || isHoe(material) || isPick(material) || isAxe(material)) {
            return true;
        }
        switch (material) {
            case SHEARS:
            case FLINT_AND_STEEL:
            case FISHING_ROD:
            case CARROT_STICK:
            default: {
                return false;
            }
        }
    }

    public static boolean isSword(Material material) {
        switch (material) {
            case IRON_SWORD:
            case STONE_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
            case WOOD_SWORD: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isSpade(Material material) {
        switch (material) {
            case IRON_SPADE:
            case STONE_SPADE:
            case GOLD_SPADE:
            case DIAMOND_SPADE:
            case WOOD_SPADE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isHoe(Material material) {
        switch (material) {
            case IRON_HOE:
            case STONE_HOE:
            case GOLD_HOE:
            case DIAMOND_HOE:
            case WOOD_HOE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isPick(Material material) {
        switch (material) {
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case GOLD_PICKAXE:
            case DIAMOND_PICKAXE:
            case WOOD_PICKAXE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isAxe(Material material) {
        switch (material) {
            case IRON_AXE:
            case STONE_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
            case WOOD_AXE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
