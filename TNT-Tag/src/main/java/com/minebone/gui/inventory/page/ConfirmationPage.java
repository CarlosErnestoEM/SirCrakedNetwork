package com.minebone.gui.inventory.page;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.ConfirmationPageButton;
import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfirmationPage<PluginType extends JavaPlugin> extends GUIPage<PluginType> {

    private ItemStack infoTop;
    private ItemStack infoMiddle;

    public ConfirmationPage(PluginType plugin, Player player, ItemStack infoTop, ItemStack infoMiddle) {
        super(plugin, player, "Confirmar?", 54);
        this.infoTop = infoTop;
        this.infoMiddle = infoMiddle;
        build();
    }

    public void buildPage() {
        ItemStack confirm = new ItemStackBuilder().setMaterial(Material.EMERALD_BLOCK).setName(ChatColor.GREEN + "CONFIRMAR");
        ItemStack cancel = new ItemStackBuilder().setMaterial(Material.REDSTONE_BLOCK).setName(ChatColor.RED + "CANCELAR");

        addButton(new PlaceHolder(infoTop), 4);
        addButton(new PlaceHolder(infoMiddle), 22);

        addButton(new ConfirmationPageButton(true, confirm), 27);
        addButton(new ConfirmationPageButton(true, confirm), 28);
        addButton(new ConfirmationPageButton(true, confirm), 29);

        addButton(new ConfirmationPageButton(true, confirm), 36);
        addButton(new ConfirmationPageButton(true, confirm), 37);
        addButton(new ConfirmationPageButton(true, confirm), 38);

        addButton(new ConfirmationPageButton(true, confirm), 45);
        addButton(new ConfirmationPageButton(true, confirm), 46);
        addButton(new ConfirmationPageButton(true, confirm), 47);

        addButton(new ConfirmationPageButton(false, cancel), 33);
        addButton(new ConfirmationPageButton(false, cancel), 34);
        addButton(new ConfirmationPageButton(false, cancel), 35);

        addButton(new ConfirmationPageButton(false, cancel), 42);
        addButton(new ConfirmationPageButton(false, cancel), 43);
        addButton(new ConfirmationPageButton(false, cancel), 44);

        addButton(new ConfirmationPageButton(false, cancel), 51);
        addButton(new ConfirmationPageButton(false, cancel), 52);
        addButton(new ConfirmationPageButton(false, cancel), 53);
    }

    public void destroy() {
        infoMiddle = null;
        infoTop = null;
    }

    public abstract void onConfirm();

    public abstract void onCancel();

}
