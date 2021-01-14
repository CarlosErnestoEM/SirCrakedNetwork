package com.pollogamer.sircrakedhub.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.manager.PlayerManager;
import com.pollogamer.sircrakedhub.manager.ServerManager;
import com.pollogamer.sircrakedhub.objects.ListServer;
import com.pollogamer.sircrakedserver.objects.GameServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class MenuServidores extends GUIPage<Principal> {

    private Player player;

    public MenuServidores(Principal plugin, Player player) {
        super(plugin, player, "§aServidores", 27);
        this.player = player;
        build();
    }

    public void buildPage() {
        for (String s : plugin.getConfig().getConfigurationSection("menu.servidores").getKeys(false)) {
            ItemStackBuilder item = new ItemStackBuilder().setName(plugin.getConfig().getString("menu.servidores." + s + ".displayname".replaceAll("&", "§"))).setMaterial(Material.getMaterial(plugin.getConfig().getInt("menu.servidores." + s + ".id"))).setStackData((short) plugin.getConfig().getInt("menu.servidores." + s + ".data")).setLore(getListColorized("menu.servidores." + s + ".lore"));
            addButton(new SimpleButton(item).setAction(((plugin1, player1, page) -> {
                String right = plugin.getConfig().getString("menu.servidores." + s + ".right");
                String left = plugin.getConfig().getString("menu.servidores." + s + ".left");
                if (getType().equals(ClickType.RIGHT)) {
                    runAction(right);
                } else if (getType().equals(ClickType.LEFT)) {
                    runAction(left);
                }
            })), plugin.getConfig().getInt("menu.servidores." + s + ".slot"));
        }
    }

    @Override
    public void destroy() {
    }

    public void runAction(String clickk) {
        String click = clickk;
        if (click != null) {
            if (click.contains("servidor")) {
                click = click.replace("servidor ", "");
                PlayerManager.sendToServer(player, click);
            } else if (click.contains("openlistserver ")) {
                click = click.replace("openlistserver ", "");
                new MenuListServer(player, click);
            } else {
                Bukkit.getServer().dispatchCommand(player, click);
            }
        }
    }

    public List<String> getListColorized(String path) {
        List<String> lista = new ArrayList<>();
        for (String s : Principal.plugin.getConfig().getStringList(path)) {
            s = ChatColor.translateAlternateColorCodes('&', s);
            if (s.contains("status_")) {
                s = s.replaceAll("status_", "");
                GameServer gameServer = ServerManager.getGameServer(s);
                if (gameServer.online) {
                    s = "§6" + gameServer.onlineplayers + (gameServer.onlineplayers == 1 ? " persona esta jugando!" : " personas estan jugando!");
                } else {
                    s = "§cEl servidor se encuentra apagado! :(";
                }
            } else if (s.contains("countall_")) {
                s = s.replaceAll("countall_", "");
                ListServer listServer = ListServer.getListServer(s);
                if (s != null) {
                    int allplayers = listServer.getAllPlayers();
                    s = "§6" + allplayers + (allplayers == 1 ? " jugador esta jugando!" : " jugadores estan jugando!");
                }
            }
            lista.add(s);
        }
        return lista;
    }
}