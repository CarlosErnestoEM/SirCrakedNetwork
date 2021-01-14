package com.pollogamer.sircrakedserver.gui;

import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.gui.inventory.page.GUIMultiPage;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class OnlinePlayers extends GUIMultiPage<SirCrakedCore> {

    private BukkitTask bukkitTask;

    public OnlinePlayers(Player player) {
        super(SirCrakedCore.getCore(), player, "Lista de jugadores");
        build();
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                refresh();
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 40, 40);
    }

    @Override
    protected void buildContent() {
        final int[] slot = {0};
        Bukkit.getOnlinePlayers().stream().skip(currentPage * 45).limit(45).forEach(player -> {
            addButton(new SimpleButton(new ItemStackBuilder().setSkullOwner(player.getName()).setName("&a" + player.getName())).setAction(((plugin1, player1, page) -> {
                player1.teleport(player.getLocation());
                player1.sendMessage(Lang.prefix + "Te has teletransportado a " + player.getName());
            })), slot[0]++);
        });
    }

    @Override
    protected int getListCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public void destroy() {
        bukkitTask.cancel();
    }
}
