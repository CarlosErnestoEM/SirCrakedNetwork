package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.Punish;
import com.pollogamer.sircrakedserver.sanciones.util.*;
import com.pollogamer.sircrakedserver.utils.TripleEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OpenSubPageButton implements GUIButton<SirCrakedCore> {

    private ItemStack item;
    private OffensePage page;
    private String punishingPlayer;
    private HistoryPage historyPage;
    private TripleEntry<List<BanRecord>, List<MuteRecord>, List<WarningRecord>> records;
    private boolean helper;

    public OpenSubPageButton(ItemStack item, OffensePage page, String punishingPlayer, boolean helper) {
        this.item = item;
        this.page = page;
        this.punishingPlayer = punishingPlayer;
        this.helper = helper;
    }

    public OpenSubPageButton(ItemStack item, OffensePage page, String punishingPlayer, HistoryPage historyPage, boolean helper) {
        this.item = item;
        this.page = page;
        this.punishingPlayer = punishingPlayer;
        this.historyPage = historyPage;
        this.helper = helper;
    }

    public ItemStack getItem() {
        return item;
    }

    public void click(GUIPage<SirCrakedCore> page) {
        final Punish p = page.getPlugin().getPunish();
        final Player pl = page.getPlayer();

        if (this.page == OffensePage.HISTORY) {
            if (historyPage == null) {
                new PunishUI(page.getPlugin(), pl, punishingPlayer, this.page, helper, null, null);
                return;
            }

            page.removeButton(historyPage.getSlot());
            page.addButton(new PlaceHolder(new ItemStackBuilder().setName(ChatColor.BLUE + "Procesando...").setMaterial(Material.LAPIS_BLOCK)), historyPage.getSlot());

            Bukkit.getServer().getScheduler().runTaskAsynchronously(page.getPlugin(), () -> {
                records = p.getRecords(punishingPlayer, historyPage);

                Bukkit.getServer().getScheduler().runTask(page.getPlugin(), () -> {
                    page.destroy();
                    page.destroyInternal();
                    new PunishUI(page.getPlugin(), pl, punishingPlayer, this.page, helper, records, historyPage);

                    this.item = null;
                    this.page = null;
                    this.punishingPlayer = null;
                });
            });

            return;
        }

        page.destroy();
        page.destroyInternal();

        if (this.page == OffensePage.MAIN) {
            if (helper) {
                new PunishUIMainPageHelper(page.getPlugin(), pl, punishingPlayer);
            } else {
                new PunishUIMainPage(page.getPlugin(), pl, punishingPlayer);
            }
        } else {
            new PunishUI(page.getPlugin(), pl, punishingPlayer, this.page, helper);
        }

        this.item = null;
        this.page = null;
        this.punishingPlayer = null;
    }

    @Override
    public void destroy() {

    }
}
