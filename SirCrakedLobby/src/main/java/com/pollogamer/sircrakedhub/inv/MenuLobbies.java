package com.pollogamer.sircrakedhub.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.manager.PlayerManager;
import com.pollogamer.sircrakedhub.manager.ServerManager;
import com.pollogamer.sircrakedserver.objects.GameServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuLobbies extends GUIPage {

    private Player player;

    public MenuLobbies(Principal plugin, Player player) {
        super(plugin, player, "Selector de lobbies", 9);
        this.player = player;
        build();
    }

    int lobbies = 0;

    @Override
    public void buildPage() {
        for (GameServer lobby : ServerManager.lobbies) {
            if (lobby.online) {
                ItemStackBuilder item = new ItemStackBuilder(Material.STAINED_CLAY).setName("§a" + lobby.getName());
                if (Bukkit.getServerName().equalsIgnoreCase(lobby.getName())) {
                    item.setStackData((short) 14);
                    item.setLore(" ", "§a" + lobby.onlineplayers + (lobby.onlineplayers == 1 ? " jugador conectado" : " jugadores conectados"), " ", " §c» §aYa estas en este Lobby!");
                } else {
                    item.setLore(" ", "§a" + lobby.onlineplayers + (lobby.onlineplayers == 1 ? " jugador conectado" : " jugadores conectados"));
                    item.setMaterial(Material.QUARTZ_BLOCK);
                }
                addButton(new SimpleButton(item).setAction(((plugin1, player, page) -> {
                    if (Bukkit.getServerName().equalsIgnoreCase(lobby.getName())) {
                        player.sendMessage("§4§lSir§1§lCraked §7» §aYa estas en esta lobby!");
                    } else {
                        PlayerManager.sendToServer(player, lobby.getBungeename());
                    }
                })), lobbies++);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
