package com.pollogamer.sircrakedffa.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedffa.manager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Kits extends GUIPage<Principal> {


    public Kits(Principal plugin, Player player) {
        super(plugin, player, "§aSelector de kit", 27);
        this.build();
    }

    public void buildPage() {
        ItemStackBuilder kitdefault = new ItemStackBuilder().setMaterial(Material.LEATHER_CHESTPLATE).setName("§aKit Normal");
        ItemStackBuilder kitvip = new ItemStackBuilder().setMaterial(Material.GOLD_CHESTPLATE).setName("\u00a71\u00a7lKit VIP");
        ItemStackBuilder kitvip2 = new ItemStackBuilder().setMaterial(Material.IRON_CHESTPLATE).setName("\u00a71\u00a7lKit VIP+");
        ItemStackBuilder kitsir = new ItemStackBuilder().setMaterial(Material.DIAMOND_CHESTPLATE).setName("\u00a71\u00a7lKit SIR");

        addButton(new SimpleButton<>(kitdefault).setAction((plugin, player, page) -> {
                    PlayerManager.setkitffadefault(player);
                }
        ), 10);
        addButton(new SimpleButton<>(kitvip).setAction((plugin, player, page) -> {
                    PlayerManager.setkitffavip(player);
                }
        ), 12);
        addButton(new SimpleButton<>(kitvip2).setAction((plugin, player, page) -> {
                    PlayerManager.setkitffavip2(player);
                }
        ), 14);
        addButton(new SimpleButton<>(kitsir).setAction((plugin, player, page) -> {
                    PlayerManager.setkitffasir(player);
                }
        ), 16);
    }

    @Override
    public void destroy() {
    }
}