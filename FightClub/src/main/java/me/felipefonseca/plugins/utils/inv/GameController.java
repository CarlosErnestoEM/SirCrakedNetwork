/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package me.felipefonseca.plugins.utils.inv;

import me.felipefonseca.plugins.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class GameController
        implements Listener {
    private final Main plugin;

    public GameController(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        if (this.plugin.getGameManager().isInLobby() && e.getPlayer().getInventory().getItemInHand() != null) {
            if (e.getPlayer().getInventory().getItemInHand().getType().equals((Object) Material.NETHER_STAR)) {
                if (e.getPlayer().hasPermission("FightClub.kits")) {
                    this.loadMenuKits(e.getPlayer());
                } else {
                    this.plugin.getMessagesController().sendMessage(e.getPlayer(), "&eNecesitas rango VIP+ o superior adquierelo en &6tienda.sircraked.com &e!");
                }
            } else if (e.getPlayer().getInventory().getItemInHand().getType().equals((Object) Material.ENDER_CHEST)) {
                if (e.getPlayer().hasPermission("FightClub.vote.life")) {
                    this.loadGameModifactorMenu(e.getPlayer());
                } else {
                    this.plugin.getMessagesController().sendMessage(e.getPlayer(), "&eNecesitas rango VIP+ o superior adquierelo en &6tienda.sircraked.com &e!");
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (this.plugin.getGameManager().isInLobby() && event.getCurrentItem() != null && event.getCurrentItem().getType() != null) {
            event.setCancelled(true);
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
                event.setCancelled(true);
                ItemStack clicked = event.getCurrentItem();
                if (clicked != null) {
                    if (event.getInventory().getTitle().equalsIgnoreCase("\u00a7a\u00a7lSelector de clase")) {
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aClase de experiencia")) {
                            if (player.hasPermission("FightClub.Experencia")) {
                                this.plugin.getSkillManager().addPlayerToEnchanterSkill(player);
                                player.closeInventory();
                                this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                            this.plugin.getMessagesController().sendActionBar(player, "&d&lSELECCIONASTE LA CLASE: &a&lEXPERIENCIA");
                                        }
                                );
                            } else {
                                this.plugin.getMessagesController().sendMessage(player, "&eNecesitas rango VIP+ o superior adquierelo en &6tienda.sircraked.com &e!");
                            }
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aClase mago")) {
                            if (player.hasPermission("FightClub.Mago")) {
                                this.plugin.getSkillManager().addPlayerToWizardSkill(player);
                                player.closeInventory();
                                this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                            this.plugin.getMessagesController().sendActionBar(player, "&d&lSELECCIONASTE LA CLASE: &a&lMAGO");
                                        }
                                );
                            } else {
                                this.plugin.getMessagesController().sendMessage(player, "&eNecesitas rango VIP+ o superior adquierelo en &6tienda.sircraked.com &e!");
                            }
                        }
                    } else if (event.getInventory().getTitle().equalsIgnoreCase("\u00a7aModificador de Juego")) {
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aModificador de vida")) {
                            this.regenerationMenu(player);
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aNiveles de vida")) {
                            this.nivelVidaMenu(player);
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aModificador de kits")) {
                            if (!player.hasPermission("FightClub.kits")) {
                                this.plugin.getMessagesController().sendMessage(player, "&eNecesitas rango SIR adquierelo en &6tienda.sircraked.com &e!");
                            } else {
                                this.kitsmenu(player);
                            }

                        }
                    } else if (event.getInventory().getTitle().equalsIgnoreCase("\u00a7aRegeneraci\u00f3n de vida")) {
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aRegeneraci\u00f3n normal")) {
                            this.plugin.getSkillManager().addVoteToNormal(player);
                            player.closeInventory();
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aRegeneraci\u00f3n UHC")) {
                            this.plugin.getSkillManager().addVoteToUHC(player);
                            player.closeInventory();
                        }
                    } else if (event.getInventory().getTitle().equalsIgnoreCase("\u00a7aNiveles de vida")) {
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aVida Normal")) {
                            this.plugin.getSkillManager().addVoteNormalVida(player);
                            player.closeInventory();
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aDoble Vida")) {
                            this.plugin.getSkillManager().addVoteDobleVida(player);
                            player.closeInventory();
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aTriple Vida")) {
                            this.plugin.getSkillManager().addVotrTripleVida(player);
                            player.closeInventory();
                        }
                    } else if (event.getInventory().getTitle().equalsIgnoreCase("\u00a7aModificador de kits")) {
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aKit Normal")) {
                            this.plugin.getSkillManager().addVoteToKitDefault(player);
                            player.closeInventory();
                        }
                        if (clicked.getItemMeta().getDisplayName().equals("\u00a7aKit BuildUHC")) {
                            this.plugin.getSkillManager().addVoteToKitBuildUHC(player);
                            player.closeInventory();
                        }
                    }
                }
            }
        }
    }

    public void loadMenuKits(Player player) {
        Inventory inv = this.plugin.getServer().createInventory(null, 9, "\u00a7a\u00a7lSelector de clase");
        ItemStack XP = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = XP.getItemMeta();
        meta.setDisplayName("\u00a7aClase de experiencia");
        XP.setItemMeta(meta);
        inv.setItem(0, XP);
        ItemStack EN = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta2 = EN.getItemMeta();
        meta2.setDisplayName("\u00a7aClase mago");
        EN.setItemMeta(meta2);
        inv.setItem(1, EN);
        player.openInventory(inv);
    }

    public void loadGameModifactorMenu(Player player) {
        Inventory Invm = this.plugin.getServer().createInventory(null, 27, "\u00a7aModificador de Juego");
        ItemStack lifeCount = new ItemStack(Material.REDSTONE);
        ItemMeta lfm = lifeCount.getItemMeta();
        lfm.setDisplayName("\u00a7aNiveles de vida");
        lfm.setLore(Arrays.asList("\u00a7aElige los niveles de vida para la partida"));
        lifeCount.setItemMeta(lfm);
        Invm.setItem(12, lifeCount);
        ItemStack health = new ItemStack(Material.EMERALD);
        ItemMeta hm = health.getItemMeta();
        hm.setDisplayName("\u00a7aModificador de vida");
        hm.setLore(Arrays.asList("\u00a7aVota por la regeneraci\u00f3n de vida normal o UHC"));
        health.setItemMeta(hm);
        Invm.setItem(13, health);
        ItemStack booster = new ItemStack(Material.POTION);
        ItemMeta bm = booster.getItemMeta();
        bm.setDisplayName("\u00a7aModificador de kits");
        bm.setLore(Arrays.asList("\u00a76Modifica por el kit BuildUHC!"));
        booster.setItemMeta(bm);
        Invm.setItem(14, booster);
        player.openInventory(Invm);
    }

    public void nivelVidaMenu(Player player) {
        Inventory vida = this.plugin.getServer().createInventory(null, 9, "\u00a7aNiveles de vida");
        ItemStack level1 = new ItemStack(Material.DOUBLE_PLANT);
        ItemMeta lm = level1.getItemMeta();
        lm.setDisplayName("\u00a7aVida Normal");
        lm.setLore(Arrays.asList("\u00a76La barra normal de vida", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getNormalVida().size()));
        level1.setItemMeta(lm);
        vida.setItem(3, level1);
        ItemStack level2 = new ItemStack(Material.DOUBLE_PLANT, 2);
        ItemMeta lm2 = level2.getItemMeta();
        lm2.setDisplayName("\u00a7aDoble Vida");
        lm2.setLore(Arrays.asList("\u00a76La doble barra de vida", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getDobleVida().size()));
        level2.setItemMeta(lm2);
        vida.setItem(4, level2);
        ItemStack level3 = new ItemStack(Material.DOUBLE_PLANT, 3);
        ItemMeta lm3 = level3.getItemMeta();
        lm3.setDisplayName("\u00a7aTriple Vida");
        lm3.setLore(Arrays.asList("\u00a76La triple barra de vida", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getTriplevida().size()));
        level3.setItemMeta(lm3);
        vida.setItem(5, level3);
        player.openInventory(vida);
    }

    public void regenerationMenu(Player player) {
        Inventory rm = this.plugin.getServer().createInventory(null, 9, "\u00a7aRegeneraci\u00f3n de vida");
        ItemStack NORMAL = new ItemStack(Material.APPLE);
        ItemMeta nm = NORMAL.getItemMeta();
        nm.setDisplayName("\u00a7aRegeneraci\u00f3n normal");
        nm.setLore(Arrays.asList("\u00a77Vota por regeneraci\u00f3n normal", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getNaturalRegeneration().size()));
        NORMAL.setItemMeta(nm);
        rm.setItem(3, NORMAL);
        ItemStack UHC = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta um = UHC.getItemMeta();
        um.setDisplayName("\u00a7aRegeneraci\u00f3n UHC");
        um.setLore(Arrays.asList("\u00a77Vota por regeneraci\u00f3n UHC", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getUhcRegeneration().size()));
        UHC.setItemMeta(um);
        rm.setItem(5, UHC);
        player.openInventory(rm);
    }

    public void kitsmenu(Player player) {
        Inventory km = this.plugin.getServer().createInventory(null, 9, "\u00a7aModificador de kits");
        ItemStack NORMAL = new ItemStack(Material.APPLE);
        ItemMeta nm = NORMAL.getItemMeta();
        nm.setDisplayName("\u00a7aKit Normal");
        nm.setLore(Arrays.asList("\u00a77Vota el kit normal", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getkitnormal().size()));
        NORMAL.setItemMeta(nm);
        km.setItem(3, NORMAL);
        ItemStack UHC = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta um = UHC.getItemMeta();
        um.setDisplayName("\u00a7aKit BuildUHC");
        um.setLore(Arrays.asList("\u00a77Vota por Kit BuildUHC", "\u00a77Votos: \u00a7e" + this.plugin.getSkillManager().getkitbuiluhc().size()));
        UHC.setItemMeta(um);
        km.setItem(5, UHC);
        player.openInventory(km);
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
    }
}

