package com.pollogamer.uhcsimulator.spectator;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import org.bukkit.entity.Player;

public class SpectatorGUI extends GUIPage<Principal> {

    public SpectatorGUI(Player player) {
        super(Principal.getPlugin(), player, "§aJugadores en juego", ((int) Math.ceil(Lang.players.size() / 9.0)) * 9);
        build();
    }

    @Override
    public void buildPage() {
        int slot = 0;
        for (Player player : Lang.players) {
            addButton(new SimpleButton(new ItemStackBuilder().setSkullOwner(player.getName()).setName("§a" + player.getName())).setAction(((plugin1, player1, page) -> {
                if (player.isOnline()) {
                    player1.teleport(player);
                    player1.sendMessage("§6§LUHCSimulator §7» §aTe has teletransportado a " + player.getName());
                }
            })), slot++);
        }
    }

    @Override
    public void destroy() {
    }
}
