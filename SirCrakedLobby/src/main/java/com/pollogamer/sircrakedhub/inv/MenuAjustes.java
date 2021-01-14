package com.pollogamer.sircrakedhub.inv;

import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedhub.Principal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class MenuAjustes extends GUIPage {

    private Player player;

    public MenuAjustes(Principal plugin, Player player) {
        super(plugin, player, "Ajustes", 45);
        this.player = player;
        build();
    }

    @Override
    public void buildPage() {
        ItemStackBuilder premium = new ItemStackBuilder().setMaterial(Material.COMMAND).setName("§aModo Premium").setLore(" ", "§7Al activar esto no te tendras", "§7que volver a loguear!", "§7No lo actives sin ser premium!");
        ItemStackBuilder premiumestado = new ItemStackBuilder().setMaterial(Material.INK_SACK).setStackData(getPremiumStatus()).setName("§aClick para activar");
        ItemStackBuilder fly = new ItemStackBuilder().setMaterial(Material.GHAST_TEAR).setName("§aModo de vuelo").setLore(" ", "§7Click para activar o desactivar");
        ItemStackBuilder flyestado = new ItemStackBuilder().setMaterial(Material.INK_SACK).setStackData(getFlyStatus()).setName("§aActiva/Desactivar");
        ItemStackBuilder ajustes = new ItemStackBuilder().setMaterial(Material.ARROW).setName("§aRegresar al menu anterior");

        addButton(new SimpleButton(premium), 12);
        addButton(new SimpleButton(premiumestado).setAction(((plugin1, player, page) -> {
            SirCrakedCore.getCore().getWrapperConnector().sendPacket("command BungeeCord premium " + player.getName());
            player.closeInventory();
        })), 21);
        addButton(new SimpleButton(fly), 14);
        addButton(new SimpleButton(flyestado).setAction(((plugin1, player, page) -> {
            Bukkit.dispatchCommand(player, "fly");
            player.closeInventory();
        })), 23);
        addButton(new SimpleButton(ajustes).setAction(((plugin1, player, page) -> {
            new MenuPerfil(Principal.plugin, player);
        })), 40);
    }

    @Override
    public void destroy() {
    }

    private short getPremiumStatus() {
        return (short) (isPremiumDB(player.getName()) ? 10 : 8);
    }

    private static boolean isPremiumDB(String playerName) {
        ResultSet resultSet = SirCrakedCore.getCore().getHikariSQLManager().Query("SELECT `enabled` FROM `premium` WHERE `username`= '" + playerName.toLowerCase() + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public short getFlyStatus() {
        if (player.getAllowFlight()) {
            return 10;
        } else {
            return 8;
        }
    }
}
