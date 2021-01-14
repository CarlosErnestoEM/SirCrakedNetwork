package com.pollogamer.uhcsimulator.walls;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class WallGenerator {
    public static Material wallBlockAir;
    public static Material wallBlockSolid;
    public static int blocksSet;

    static {
        WallGenerator.wallBlockAir = null;
        WallGenerator.wallBlockSolid = null;
        WallGenerator.blocksSet = 0;
    }

    public abstract void build(final World p0, final int p1, final int p2);

    public static void setBlock(final Block block, final WallPosition wallPosition) {
        if (isBlockTransparentOrNatural(block.getType())) {
            block.setType(Material.BEDROCK);
        } else if (isBlockTransparentOrNatural(getInnerBlock(block, wallPosition).getType())) {
            block.setType(Material.BEDROCK);
        } else {
            block.setType(Material.BEDROCK);
        }
        ++WallGenerator.blocksSet;
    }

    protected static Boolean isBlockTransparentOrNatural(final Material material) {
        if (material.isTransparent()) {
            return true;
        }
        switch (material) {
            case WATER:
            case STATIONARY_WATER:
            case LOG:
            case LEAVES:
            case GLASS:
            case BED_BLOCK:
            case PISTON_STICKY_BASE:
            case PISTON_BASE:
            case BOOKSHELF:
            case MOB_SPAWNER:
            case CHEST:
            case SIGN_POST:
            case WALL_SIGN:
            case ICE:
            case CACTUS:
            case FENCE:
            case PUMPKIN:
            case GLOWSTONE:
            case JACK_O_LANTERN:
            case CAKE_BLOCK:
            case STAINED_GLASS:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case IRON_FENCE:
            case THIN_GLASS:
            case MELON_BLOCK:
            case FENCE_GATE:
            case NETHER_FENCE:
            case ENDER_CHEST:
            case BEACON:
            case COBBLE_WALL:
            case ANVIL:
            case TRAPPED_CHEST:
            case STAINED_GLASS_PANE:
            case LEAVES_2:
            case LOG_2:
            case PACKED_ICE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    protected static Block getInnerBlock(final Block block, final WallPosition wallPosition) {
        final World world = block.getWorld();
        final Integer value = block.getX();
        final Integer value2 = block.getY();
        final Integer value3 = block.getZ();
        switch (wallPosition) {
            case EAST: {
                return world.getBlockAt(value - 1, (int) value2, (int) value3);
            }
            case NORTH: {
                return world.getBlockAt((int) value, (int) value2, value3 + 1);
            }
            case SOUTH: {
                return world.getBlockAt((int) value, (int) value2, value3 - 1);
            }
            case WEST: {
                return world.getBlockAt(value + 1, (int) value2, (int) value3);
            }
            default: {
                return null;
            }
        }
    }

    public int getBlocksSet() {
        return WallGenerator.blocksSet;
    }
}
