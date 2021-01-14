package com.pollogamer.sircrakedffa.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedffa.manager.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ModosDeJuego extends GUIPage<Principal> {


    public ModosDeJuego(Principal plugin, Player player) {
        super(plugin, player, "§aModos de FFA", 9);
        this.build();
    }

    public void buildPage() {

        ItemStackBuilder FFA = new ItemStackBuilder().setMaterial(Material.IRON_SWORD).setName("§bFFA");
        ItemStackBuilder BuildFFA = new ItemStackBuilder().setMaterial(Material.LAVA_BUCKET).setName("§bBuildFFA");
        ItemStackBuilder ComboFFA = new ItemStackBuilder().setMaterial(Material.DIAMOND_SWORD).setName("§bCombo");

        addButton(new SimpleButton<>(FFA).setAction((plugin, player, page) -> player.teleport(Lang.ffa)), 2);
        addButton(new SimpleButton<>(BuildFFA).setAction((plugin, player, page) -> Bukkit.dispatchCommand(player, "buildffa")), 4);
        addButton(new SimpleButton<>(ComboFFA).setAction((plugin, player, page) -> Bukkit.dispatchCommand(player, "comboffa")), 6);
    }

    public void destroy() {
    }
}