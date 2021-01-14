package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.page.ConfirmationPage;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.util.Offense;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PunishButton implements GUIButton<SirCrakedCore> {

    private ItemStack item;
    private String punishedPlayer;
    private Offense offense;
    private String reason;

    public PunishButton(ItemStack item, String punishedPlayer, Offense offense, String reason) {
        this.item = item;
        this.punishedPlayer = punishedPlayer;
        this.offense = offense;
        this.reason = reason;
    }

    @Override
    public void click(GUIPage<SirCrakedCore> page) {
        new ConfirmationPage<SirCrakedCore>(page.getPlugin(), page.getPlayer(), new ItemStackBuilder(Material.BOOK_AND_QUILL).setName(ChatColor.GREEN + "Confirmar la siguiente sanción?"), item) {
            @Override
            public void onConfirm() {
                new PunishConfirmButton(item, punishedPlayer, offense, reason).click(this);
            }

            @Override
            public void onCancel() {
                if (getPlayer().hasPermission("sircraked.s.advanced")) {
                    new PunishUIMainPage(plugin, getPlayer(), punishedPlayer);
                } else {
                    new PunishUIMainPageHelper(plugin, getPlayer(), punishedPlayer);
                }
            }
        };
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void destroy() {
    }
}
