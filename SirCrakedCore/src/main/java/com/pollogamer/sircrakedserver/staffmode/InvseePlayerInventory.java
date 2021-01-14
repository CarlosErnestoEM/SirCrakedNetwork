package com.pollogamer.sircrakedserver.staffmode;

import com.minebone.itemstack.ItemStackBuilder;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseePlayerInventory extends PlayerInventory {

    private CraftInventory inventory;
    private ItemStack[] extra;
    private CraftPlayer owner;

    public InvseePlayerInventory(final Player player) {
        /*

               addButton(new SimpleButton(new ItemStackBuilder(Material.SPECKLED_MELON).setName("&aNivel de vida").setStackAmount((int)(obj.getHealth()))),45);
        addButton(new SimpleButton(new ItemStackBuilder(Material.COOKED_BEEF).setName("&aHambre").setStackAmount((obj.getFoodLevel()))),46);
        addButton(new SimpleButton(new ItemStackBuilder(Material.EXP_BOTTLE).setName("&aNivel de XP").setStackAmount(obj.getLevel())),51);
         */
        super(((CraftPlayer) player).getHandle());
        this.inventory = new CraftInventory(this);
        this.extra = new ItemStack[5];
        this.owner = (CraftPlayer) player;
        this.items = this.player.inventory.items;
        this.armor = this.player.inventory.armor;
        extra[0] = CraftItemStack.asNMSCopy(new ItemStackBuilder(Material.SPECKLED_MELON).setName("&aNivel de vida").setStackAmount((int) (player.getHealth())));
        extra[1] = CraftItemStack.asNMSCopy(new ItemStackBuilder(Material.COOKED_BEEF).setName("&aHambre").setStackAmount((player.getFoodLevel())));
        extra[2] = CraftItemStack.asNMSCopy(new ItemStackBuilder(Material.EXP_BOTTLE).setName("&aNivel de XP").setStackAmount(player.getLevel()));
    }

    public Inventory getBukkitInventory() {
        return this.inventory;
    }

    public void onClose(final CraftHumanEntity who) {
        super.onClose(who);
    }

    public ItemStack[] getContents() {
        final ItemStack[] C = new ItemStack[this.getSize()];
        System.arraycopy(this.items, 0, C, 0, this.items.length);
        System.arraycopy(this.armor, 0, C, this.items.length, this.armor.length);
        return C;
    }

    public int getSize() {
        return super.getSize() + 5;
    }

    public ItemStack getItem(int i) {
        ItemStack[] is = this.items;
        if (i >= is.length) {
            i -= is.length;
            is = this.armor;
        } else {
            i = this.getReversedItemSlotNum(i);
        }
        if (i >= is.length) {
            i -= is.length;
            is = this.extra;
        } else if (is == this.armor) {
            i = this.getReversedArmorSlotNum(i);
        }
        return is[i];
    }

    public ItemStack splitStack(int i, final int j) {
        ItemStack[] is = this.items;
        if (i >= is.length) {
            i -= is.length;
            is = this.armor;
        } else {
            i = this.getReversedItemSlotNum(i);
        }
        if (i >= is.length) {
            i -= is.length;
            is = this.extra;
        } else if (is == this.armor) {
            i = this.getReversedArmorSlotNum(i);
        }
        if (is[i] == null) {
            return null;
        }
        if (is[i].count <= j) {
            final ItemStack itemstack = is[i];
            is[i] = null;
            return itemstack;
        }
        final ItemStack itemstack = is[i].cloneAndSubtract(j);
        if (is[i].count == 0) {
            is[i] = null;
        }
        return itemstack;
    }

    public ItemStack splitWithoutUpdate(int i) {
        ItemStack[] is = this.items;
        if (i >= is.length) {
            i -= is.length;
            is = this.armor;
        } else {
            i = this.getReversedItemSlotNum(i);
        }
        if (i >= is.length) {
            i -= is.length;
            is = this.extra;
        } else if (is == this.armor) {
            i = this.getReversedArmorSlotNum(i);
        }
        if (is[i] != null) {
            final ItemStack itemstack = is[i];
            is[i] = null;
            return itemstack;
        }
        return null;
    }

    public void setItem(int i, ItemStack itemstack) {
        ItemStack[] is = this.items;
        if (i >= is.length) {
            i -= is.length;
            is = this.armor;
        } else {
            i = this.getReversedItemSlotNum(i);
        }
        if (i >= is.length) {
            i -= is.length;
            is = this.extra;
        } else if (is == this.armor) {
            i = this.getReversedArmorSlotNum(i);
        }
        if (is == this.extra && i > 42) {
            this.owner.getHandle().drop(itemstack, true);
            itemstack = null;
        }
        is[i] = itemstack;
        this.owner.getHandle().defaultContainer.b();
    }

    private int getReversedItemSlotNum(final int i) {
        return (i >= 27) ? (i - 27) : (i + 9);
    }

    private int getReversedArmorSlotNum(final int i) {
        if (i == 0) {
            return 3;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 0;
        }
        return i;
    }

    public boolean hasCustomName() {
        return true;
    }

    public String getName() {
        return this.player.getName();
    }

    public boolean a(final EntityHuman entityhuman) {
        return true;
    }

    public void update() {
        super.update();
        this.player.inventory.update();
    }
}