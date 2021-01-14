package com.pollogamer.sircrakedserver.staffmode;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StaffInvsee extends GUIPage<SirCrakedCore> {

    private Player obj;

    public StaffInvsee(Player player, Player obj) {
        super(SirCrakedCore.getCore(), player, "§7" + obj.getName(), 54);
        this.obj = obj;
        build();
    }

    @Override
    public void buildPage() {
        menu.setContents(obj.getInventory().getContents());
        setArmorItems();
        setOtherItems();
        fillEmptySlots();
    }

    @Override
    public void destroy() {
    }

    public void setArmorItems() {
        addButton(new SimpleButton(obj.getInventory().getHelmet()), 36);
        addButton(new SimpleButton(obj.getInventory().getChestplate()), 37);
        addButton(new SimpleButton(obj.getInventory().getLeggings()), 38);
        addButton(new SimpleButton(obj.getInventory().getBoots()), 39);
    }

    public void setOtherItems() {
        addButton(new SimpleButton(new ItemStackBuilder(Material.SPECKLED_MELON).setName("&aNivel de vida").setStackAmount((int) (obj.getHealth()))), 45);
        addButton(new SimpleButton(new ItemStackBuilder(Material.COOKED_BEEF).setName("&aHambre").setStackAmount((obj.getFoodLevel()))), 46);
        addButton(new SimpleButton(new ItemStackBuilder(Material.EXP_BOTTLE).setName("&aNivel de XP").setStackAmount(obj.getLevel())), 51);
    }

    public void fillEmptySlots() {
        for (int i = 36; i < 54; i++) {
            if (menu.getItem(i) == null) {
                addButton(new SimpleButton(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setStackData((short) 14).setName("")), i);
            }
        }
    }
}
