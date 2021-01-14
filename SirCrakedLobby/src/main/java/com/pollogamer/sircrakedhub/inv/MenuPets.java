package com.pollogamer.sircrakedhub.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedserver.coins.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuPets extends GUIPage<Principal> {


    public MenuPets(Principal plugin, Player player) {
        super(plugin, player, "Mascotas", 54);
        this.build();
    }

    public void buildPage() {
        ItemStackBuilder itemstack = new ItemStackBuilder().setSkullOwner(getPlayer().getName()).setName("\u00a7eMini Aventurero");

        if (!this.getPlayer().hasPermission("minime.use")) {
            itemstack.addLore("\u00a77Ten un mini tu de mascota!", "  ", "\u00a7aCosto 500 SirCoins", "\u00a7aO encuentralo en las cajas legendarias!");
        } else {
            itemstack.addLore("\u00a77Ten un mini tu de mascota!", "  ", "\u00a7a\u00a7lCOMPRADO", "\u00a7aO encuentralo en las cajas legendarias!");
        }

        this.addButton(new SimpleButton<>(itemstack).setAction((plugin1, player, page) -> {
            if (player.hasPermission("minime.use")) {
                player.sendMessage("\u00a7e\u00a7lPets \u00a77\u00bb \u00a7aYa tienes esta mascota!");
                return;
            }
            if (CoinsAPI.getCoins(player) > 500) {
                CoinsAPI.removeCoins(player, 500);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) ("pex user " + player.getName() + " add minime.use"));
                player.sendMessage("\u00a7e\u00a7lPets \u00a77\u00bb \u00a7aHas comprado esta mascota!");
            }
        }), 18);

        this.addButton(new SimpleButton<>(new ItemStackBuilder().setMaterial(Material.BARRIER).setName("\u00a7aRemover mascota")).setAction((plugin1, player, page) -> Bukkit.dispatchCommand(player, "minime kill")), 4);
        this.addButton(new SimpleButton<>(new ItemStackBuilder().setMaterial(Material.PAPER).setName("\u00a7aInformacion").addLore("\u00a7aLa mascota te seguira a todas partes", "\u00a7a")), 2);

        this.addButton(new SimpleButton<>(new ItemStackBuilder().setMaterial(Material.ANVIL).setName("\u00a7aModificar tu mascota")).setAction((plugin1, player, page) -> Bukkit.dispatchCommand(player, "minime")), 6);
    }

    @Override
    public void destroy() {
    }
}