package com.pollogamer.sircrakedhub.inv;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PermissionPlayer;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.button.SimpleButton;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedserver.objects.SirPlayer;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuPerfil extends GUIPage {


    private Principal plugin;
    private Player p;
    private SirPlayer sirPlayer;
    private PowerfulPermsPlugin pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    private PermissionManager permissionManager = pp.getPermissionManager();

    public MenuPerfil(Principal plugin, Player player) {
        super(plugin, player, "§aMi Perfil", 27);
        this.p = player;
        this.sirPlayer = SirPlayer.getPlayer(p);
        this.plugin = plugin;
        build();
    }

    @Override
    public void buildPage() {
        PermissionPlayer ps = permissionManager.getPermissionPlayer(p.getUniqueId());
        ItemStackBuilder info = new ItemStackBuilder().setSkullOwner(p.getName()).setName("§e§lINFORMACION");
        info.setLore(" ", "§7TU", "  ", "§6Rango: §7" + ps.getPrimaryGroup().getName(), "§6SirCoins: " + sirPlayer.getCoins(), "  ", "§7Servidor", "   ", "§aWEB: SirCraked.com", "§aTS: ts3.sircraked.com", "§aTienda: tienda.sircraked.com", "§aTwitter: @SirCrakedMC", "    ");
        ItemStackBuilder lobbies = new ItemStackBuilder(Material.NETHER_STAR).setName("§aSelector de lobbies").setLore(" ", "§7Puedes cambiar de lobbies para", "§7estar tus amigos!", "  ");
        ItemStackBuilder ajustes = new ItemStackBuilder(Material.REDSTONE_COMPARATOR).setName("§aAjustes").setLore(" ", " §aCambia los ajustes como", "§aFly, Modo Premium, Etc");
        ItemStackBuilder servidores = new ItemStackBuilder(Material.COMPASS).setName("§aServidores").setLore(" ", "§7Abre el menu de servidores");
        ItemStackBuilder nivel = new ItemStackBuilder(Material.BREWING_STAND_ITEM).setName("§aNivel de SirCraked").setLore("§7Juega y completa las quest para", "§7conseguir SirCoins y EXP!", " ", "§ANivel " + Utils.getLevelBar(sirPlayer) + " §9" + Utils.getPercentage(sirPlayer) + "%", " ", "§7Exp para el siguiente nivel: §cMANTENIMIENTO", " ", "§e¡Haz click para ver las recompensas!");

        addButton(new SimpleButton(info), 4);
        addButton(new SimpleButton(servidores).setAction(((plugin1, player, page) -> new MenuServidores(plugin, p))), 12);
        addButton(new SimpleButton(lobbies).setAction(((plugin1, player, page) -> new MenuLobbies(plugin, p))), 13);
        addButton(new SimpleButton(ajustes).setAction(((plugin1, player, page) -> new MenuAjustes(Principal.plugin, p))), 14);
        addButton(new SimpleButton(nivel).setAction(((plugin1, player, page) -> p.sendMessage(Lang.prefix + "Trabajando en ello"))), 22);

    }

    @Override
    public void destroy() {
    }
}
