package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.PlaceHolder;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.util.OffensePage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PunishUIMainPage extends GUIPage<SirCrakedCore> {

    private String punishedPlayer;

    public PunishUIMainPage(SirCrakedCore plugin, Player player, String punishedPlayer) {
        super(plugin, player, "Castigos", 18);
        this.punishedPlayer = punishedPlayer;
        build();
    }

    public void buildPage() {
        ItemStackBuilder itemBuilder1 = new ItemStackBuilder();
        itemBuilder1.setMaterial(Material.SKULL_ITEM);
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(punishedPlayer);
        itemBuilder1.setItemMeta(meta);
        itemBuilder1.setStackData((short) 3);
        itemBuilder1.setName(ChatColor.GREEN + punishedPlayer);
        addButton(new PlaceHolder(itemBuilder1), 4);

        ItemStack chatOffense = new ItemStackBuilder().setMaterial(Material.BOOK_AND_QUILL).setName(ChatColor.GREEN + "Sanciones de Chat").setLore(ChatColor.GRAY + "Insultos/Publicidad/Flood etc");
        ItemStack gameplayOffense = new ItemStackBuilder().setMaterial(Material.MAP).setName(ChatColor.GREEN + "Sanciones de Juego").setLore(ChatColor.GRAY + "Salirse del mapa/Aprovecho de bugs etc");
        ItemStack permanentPunishments = new ItemStackBuilder().setMaterial(Material.BEDROCK).setName(ChatColor.GREEN + "Sanciones Permanentes").setLore(ChatColor.GRAY + "Sancion Permanente/Silencio Permanente");
        ItemStack punishmentHistory = new ItemStackBuilder().setMaterial(Material.PAPER).setName(ChatColor.GREEN + "Historial de Castigos").setLore(ChatColor.GRAY + "Todas las sanciones previas");

        addButton(new OpenSubPageButton(chatOffense, OffensePage.CHAT, punishedPlayer, false), 10);
        addButton(new OpenSubPageButton(gameplayOffense, OffensePage.GAMEPLAY, punishedPlayer, false), 12);
        addButton(new OpenSubPageButton(permanentPunishments, OffensePage.PERMANENT, punishedPlayer, false), 14);
        addButton(new OpenSubPageButton(punishmentHistory, OffensePage.HISTORY, punishedPlayer, false), 16);

    }

    @Override
    public void destroy() {

    }
}
