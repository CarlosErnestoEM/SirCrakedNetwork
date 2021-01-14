package com.pollogamer.builduhc.gui;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.builduhc.Principal;
import com.pollogamer.builduhc.manager.PlayerManager;
import com.pollogamer.builduhc.objects.ListServer;
import com.pollogamer.sircrakedserver.objects.GameServer;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class MenuListServer extends GUIPage {

    private String type;
    private BukkitTask task;
    private ListServer listServer;

    public MenuListServer(Player player, String type) {
        super(SirCrakedCore.getCore(), player, "Selecciona tu arena", 45);
        this.type = type;
        listServer = ListServer.getListServer(type);
        build();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                refresh();
            }
        }.runTaskTimer(Principal.getPlugin(), 4, 4);
    }

    @Override
    public void buildPage() {
        if (listServer != null) {
            int slot = 11;
            int serversadded = 0;
            for (GameServer gameServer : listServer.getGameServers()) {
                if (gameServer.online) {
                    if (listServer.isAllowToJoin(gameServer)) {
                        addButton(new SimpleButton(getItem(listServer, gameServer)).setAction(((plugin1, player1, page) -> {
                            player1.sendMessage(Lang.prefix + "§eSeras mandado al servidor " + gameServer.getName());
                            PlayerManager.sendToServer(player1, gameServer.getBungeename());
                            player1.closeInventory();
                        })), slot++);
                        serversadded++;
                        if (slot == 15 || slot == 24) {
                            slot = +5;
                        }
                    }
                }
            }
            if (serversadded == 0) {
                addButton(new SimpleButton(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setStackData((short) 14).setName("§cNo encontramos ningun servidor disponible :(")), 22);
            }

            addButton(new SimpleButton(new ItemStackBuilder(Material.ARROW).setName("§cCerrar")).setAction(((plugin1, player1, page) -> player1.closeInventory())), 36);
            addButton(new SimpleButton(new ItemStackBuilder(Material.ARROW).setName("§cCerrar")).setAction(((plugin1, player1, page) -> player1.closeInventory())), 44);
        } else {
            for (int i = 0; i < size; i++) {
                addButton(new SimpleButton(new ItemStackBuilder(Material.REDSTONE_BLOCK).setName("§cList Server no existente")), i);
            }
        }
    }

    @Override
    public void destroy() {
        task.cancel();
    }

    public ItemStackBuilder getItem(ListServer listServer, GameServer gameServer) {
        String string = getString("guilistserver." + getState(listServer, gameServer) + ".item");
        String[] itemm = string.split(":");
        return new ItemStackBuilder(Material.getMaterial(Integer.parseInt(itemm[0]))).setStackData((short) Integer.parseInt(itemm[1])).setName(gameServer.getName()).setLore(getListColorized(listServer, gameServer)).setStackAmount(gameServer.onlineplayers);
    }

    public String getState(ListServer listServer, GameServer gameServer) {
        if (listServer.getAllowedstages().contains(gameServer.motd)) {
            return "waiting";
        } else {
            return "starting";
        }
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', Principal.getPlugin().getServers().getString(path));
    }

    public List<String> getListColorized(ListServer listServer, GameServer gameServer) {
        List<String> lista = new ArrayList<>();
        for (String s : Principal.getPlugin().getServers().getStringList("guilistserver." + getStage(listServer, gameServer) + ".lore")) {
            s = s.replaceAll("%motd%", gameServer.motd);
            s = s.replaceAll("%players%", gameServer.onlineplayers + "");
            s = s.replaceAll("%maxplayers%", gameServer.maxplayers + "");
            s = ChatColor.translateAlternateColorCodes('&', s);
            lista.add(s);
        }
        return lista;
    }

    public String getStage(ListServer listServer, GameServer gameServer) {
        String stage = null;
        if (listServer.getAllowedstages().contains(gameServer.motd)) {
            stage = "waiting";
        } else if (listServer.getStartingstages().contains(gameServer.motd)) {
            stage = "starting";
        }
        return stage;
    }
}
