package com.pollogamer.tnttag.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.tnttag.Principal;
import com.pollogamer.tnttag.listener.PlayerListener;
import org.bukkit.entity.Player;

public class Teleporter extends GUIPage {

    Player p;
    int slot = 0;

    public Teleporter(Principal plugin, Player player) {
        super(plugin, player, "Jugadores", ((int) Math.ceil(PlayerListener.players.size() / 9.0)) * 9);
        p = player;
        build();
    }


    @Override
    public void buildPage() {
        for (Player player : PlayerListener.players) {
            addButton(new SimpleButton(new ItemStackBuilder().setSkullOwner(player.getName()).setName("§a" + player.getName())).setAction(((plugin1, playe, page) -> {
                if (player != null) {
                    p.teleport(player.getLocation());
                    p.sendMessage("§aEstas especteando a " + player.getName());
                } else {
                    p.sendMessage("§cEl jugador ya no esta conectado!");
                }
            })), slot);
            slot++;
        }
    }

    @Override
    public void destroy() {
    }
}
