package com.pollogamer.sircrakedserver.troll;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.entity.Player;

public class GUITroll extends GUIPage<SirCrakedCore> {

    private Player trolled;

    public GUITroll(Player player, Player trolled) {
        super(SirCrakedCore.getCore(), player, "Troll " + trolled.getName(), 45);
        this.trolled = trolled;
        build();
    }

    @Override
    public void buildPage() {
        addButton(new SimpleButton(new ItemStackBuilder().setSkullOwner(trolled.getName()).setName("§a" + trolled.getName())), 4);
        for (AbstractTroll abstractTroll : TrollManager.trolls) {
            addButton(new SimpleButton(abstractTroll.itemStackBuilder).setAction(((plugin1, player, page) -> {
                if (!abstractTroll.gui) {
                    getPlayer().sendMessage(Lang.prefix + "Aplicando el troll " + abstractTroll.name + " a " + trolled.getName());
                    abstractTroll.action(player, trolled);
                    player.closeInventory();
                }
            })), abstractTroll.slot);
        }
    }

    @Override
    public void destroy() {
    }
}
