package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.gui.inventory.page.GUIMultiPage;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.util.*;
import com.pollogamer.sircrakedserver.utils.TripleEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class PunishUI extends GUIMultiPage {

    private OffensePage page;
    private String punishingPlayer;
    private int slot;
    private int subSlot;
    private TripleEntry<List<BanRecord>, List<MuteRecord>, List<WarningRecord>> records;
    private HistoryPage historyPage;
    private boolean helper;

    public PunishUI(SirCrakedCore plugin, Player player, String punishingPlayer, OffensePage page, boolean helper, TripleEntry<List<BanRecord>, List<MuteRecord>, List<WarningRecord>> records, HistoryPage historyPage) {
        super(plugin, player, page.getTitle(), historyPage == null ? 27 : 54);
        this.page = page;
        this.punishingPlayer = punishingPlayer;
        this.records = records;
        this.helper = helper;
        this.historyPage = historyPage;
        this.pageSize = 36;
        build();
    }

    public PunishUI(SirCrakedCore plugin, Player player, String punishingPlayer, OffensePage page, boolean helper) {
        super(plugin, player, page.getTitle());
        this.page = page;
        this.punishingPlayer = punishingPlayer;
        this.helper = helper;
        build();
    }

    public void buildContent() {
        ItemStackBuilder itemBuilder1 = new ItemStackBuilder();
        itemBuilder1.setMaterial(Material.SKULL_ITEM);

        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(punishingPlayer);

        itemBuilder1.setStackData((short) 3);
        itemBuilder1.setItemMeta(meta);
        itemBuilder1.setName(ChatColor.GREEN + punishingPlayer);

        addButton(new PlaceHolder(itemBuilder1), 4);

        if (page == OffensePage.HISTORY) {
            if (historyPage == null) {

                addButton(new OpenSubPageButton(HistoryPage.BAN.getItemStack(), OffensePage.HISTORY, punishingPlayer, HistoryPage.BAN, helper), HistoryPage.BAN.getSlot());
                addButton(new OpenSubPageButton(HistoryPage.MUTE.getItemStack(), OffensePage.HISTORY, punishingPlayer, HistoryPage.MUTE, helper), HistoryPage.MUTE.getSlot());
                addButton(new OpenSubPageButton(HistoryPage.WARNING.getItemStack(), OffensePage.HISTORY, punishingPlayer, HistoryPage.WARNING, helper), HistoryPage.WARNING.getSlot());

                ItemStack item = new ItemStackBuilder().setMaterial(Material.ARROW).setName(ChatColor.GREEN + "Ir atrás");
                addButton(new OpenSubPageButton(item, OffensePage.MAIN, punishingPlayer, helper), 22);
                return;
            }

            int slot = 9;

            for (int i = currentPage * 36; i < (currentPage * 36) + 36; i++) {
                //noinspection unchecked
                List<IRecord> iRecordList = (List<IRecord>) (historyPage == HistoryPage.BAN ? records.getValue1() : historyPage == HistoryPage.MUTE ? records.getValue2() : records.getValue3());
                if (iRecordList.size() <= i) {
                    break;
                }

                addButton(new DeletePunishButton(iRecordList.get(i)), slot);
                slot++;
            }

            ItemStack item = new ItemStackBuilder().setMaterial(Material.ARROW).setName(ChatColor.GREEN + "Ir atrás");
            addButton(new OpenSubPageButton(item, OffensePage.HISTORY, punishingPlayer, helper), 47);
            return;
        } else {
            slot = (helper ? page.getStartSlotHelper() : page.getStartSlot());
            subSlot = 0;

            Arrays.stream(Offense.values()).filter(o -> o.getType() == page).forEach(offense -> {
                if (!offense.isHelper() && helper) {
                    return;
                }

                if (offense.isNewColumn()) {
                    slot++;
                    subSlot = 0;
                    if (slot == 22 && page != OffensePage.PERMANENT && page != OffensePage.GAMEPLAY && page != OffensePage.CHAT) {
                        slot++;
                    }

                    if ((page == OffensePage.PERMANENT || page == OffensePage.GAMEPLAY) && (slot == 21 || slot == 23)) {
                        slot++;
                    }
                }

                ItemStackBuilder itemBuilder = new ItemStackBuilder();
                itemBuilder.setMaterial(Material.INK_SACK);
                itemBuilder.setStackData(offense.getSeverity().getStackData());
                itemBuilder.setName(ChatColor.GREEN + offense.getSeverity().getDisplayName());
                itemBuilder.setLore(ChatColor.GRAY + "Castigo: " + ChatColor.GOLD + offense.getPuinishmentType().getDisplayName(), ChatColor.GRAY + "Duración del castigo: " + ChatColor.GOLD + (offense.getTime().equalsIgnoreCase("69d") ? "Permanente" : offense.getTime()), " ", ChatColor.GRAY + offense.getOffense().getReason(), offense.getOffense().getExamples().length == 0 ? "" : "  " + ChatColor.WHITE + offense.getOffense().getExamples()[0]);
                addButton(new PunishButton(itemBuilder, punishingPlayer, offense, offense.getReason()), slot + subSlot);

                subSlot += 9;
            });
        }

        ItemStack item = new ItemStackBuilder().setMaterial(Material.ARROW).setName(ChatColor.GREEN + "Ir al menu principal");
        addButton(new OpenSubPageButton(item, OffensePage.MAIN, punishingPlayer, helper), 49);
    }

    @Override
    protected int getListCount() {
        if (historyPage == null) {
            return -1;
        }

        switch (historyPage) {
            case MUTE:
                return records.getValue2().size();
            case BAN:
                return records.getValue1().size();
            case WARNING:
                return records.getValue3().size();
        }

        return -1;
    }

    @Override
    public void destroy() {
        this.page = null;
    }
}
