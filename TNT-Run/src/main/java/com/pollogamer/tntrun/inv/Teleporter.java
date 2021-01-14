package com.pollogamer.tntrun.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.tntrun.Principal;
import com.pollogamer.tntrun.listener.PlayerListener;
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
        for (Player p : PlayerListener.players) {
            addButton(new SimpleButton(new ItemStackBuilder().setSkullOwner(p.getName()).setName("§a" + p.getName())).setAction(((plugin1, player, page) -> {
                if (p != null) {
                    player.teleport(p.getLocation());
                    player.sendMessage("§aEstas especteando a " + p.getName());
                } else {
                    player.sendMessage("§cEl jugador ya no esta conectado!");
                }
            })), slot);
            slot++;
        }
    }

    @Override
    public void destroy() {
    }
}
