/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.utils.ItemLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Random;

public class GameControllerManager {
    private final Main plugin;
    private final ArrayList<Player> _enchanterSkill;
    private final ArrayList<Player> _wizardSkill;
    private final ArrayList<Player> _naturalRegeneration;
    private final ArrayList<Player> _uhcRegeneration;
    private final ArrayList<Player> normalVida;
    private final ArrayList<Player> dobleVida;
    private final ArrayList<Player> triplevida;
    private final ArrayList<Player> kitnormal;
    private final ArrayList<Player> kitbuilduhc;
    private final Random rand;

    public GameControllerManager(Main plugin) {
        this.plugin = plugin;
        this._enchanterSkill = new ArrayList();
        this._wizardSkill = new ArrayList();
        this._naturalRegeneration = new ArrayList();
        this._uhcRegeneration = new ArrayList();
        this.normalVida = new ArrayList();
        this.dobleVida = new ArrayList();
        this.triplevida = new ArrayList();
        this.rand = new Random();
        this.kitnormal = new ArrayList();
        this.kitbuilduhc = new ArrayList();
    }

    public void addVoteHealth(Player player, ArrayList toVote, ArrayList contais, ArrayList contais2) {
        if (toVote.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por esta opcion.");
        } else if (contais.contains((Object) player) || contais2.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa has votado por otra opcion");
        } else {
            toVote.add(player);
        }
    }

    public ArrayList<Player> getNormalVida() {
        return this.normalVida;
    }

    public ArrayList<Player> getDobleVida() {
        return this.dobleVida;
    }

    public ArrayList<Player> getTriplevida() {
        return this.triplevida;
    }

    public void addVoteNormalVida(Player player) {
        this.addVoteHealth(player, this.normalVida, this.dobleVida, this.triplevida);
        this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&lvida normal");
        this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &evida normal&7.");
    }

    public void addVoteDobleVida(Player player) {
        this.addVoteHealth(player, this.dobleVida, this.normalVida, this.triplevida);
        this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&ldoble vida");
        this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &edoble vida&7.");
    }

    public void addVotrTripleVida(Player player) {
        this.addVoteHealth(player, this.triplevida, this.normalVida, this.dobleVida);
        this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&ltriple vida");
        this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &etriple vida&7.");
    }

    public void checkVida() {
        if (this.normalVida.size() >= 1 && this.dobleVida.size() < this.normalVida.size() && this.triplevida.size() < this.normalVida.size()) {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lVida normal");
        } else if (this.dobleVida.size() >= 1 && this.normalVida.size() < this.dobleVida.size() && this.triplevida.size() < this.dobleVida.size()) {
            this.plugin.getGameManager().getPlayers().stream().map(players -> {
                        players.setMaxHealth(40);
                        return players;
                    }
            ).forEach(players -> {
                        players.setHealth(players.getMaxHealth());
                    }
            );
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lVida doble");
        } else if (this.triplevida.size() >= 1 && this.normalVida.size() < this.triplevida.size() && this.normalVida.size() < this.triplevida.size()) {
            this.plugin.getGameManager().getPlayers().stream().map(players -> {
                        players.setMaxHealth(50);
                        return players;
                    }
            ).forEach(players -> {
                        players.setHealth(players.getMaxHealth());
                    }
            );
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lVida triple");
        } else if (this.normalVida.isEmpty() && this.dobleVida.isEmpty() && this.triplevida.isEmpty()) {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lVida normal");
        } else {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lVida normal");
        }
    }

    public void checkRegeneracion() {
        if (this._naturalRegeneration.size() > this._uhcRegeneration.size()) {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lRegeneracion Normal");
        } else if (this._uhcRegeneration.size() > this._naturalRegeneration.size()) {
            this.plugin.getArenaWorld().setGameRuleValue("naturalRegeneration", "false");
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lRegeneracion UHC");
        } else if (this._naturalRegeneration.size() == this._uhcRegeneration.size()) {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lRegeneracion Normal");
        } else {
            this.plugin.getMessagesController().sendBroadcast("&c&lModificaciones: &e&lRegeneracion UHC");
        }
    }

    public void CheckKit(Player player) {
        if (this.kitnormal.size() > this.kitbuilduhc.size()) {
            this.plugin.getMessagesController().sendMessage(player, "&c&lModificaciones: &e&lKit Normal");
            this.plugin.getPlayerManager().setDefaultKit(player);
        } else if (this.kitbuilduhc.size() > this.kitnormal.size()) {
            this.plugin.getMessagesController().sendMessage(player, "&c&lModificaciones: &e&lKit BuildUHC");
            this.plugin.getPlayerManager().setUHCKit(player);
        } else if (this.kitbuilduhc.size() == this.kitnormal.size()) {
            this.plugin.getMessagesController().sendMessage(player, "&c&lModificaciones: &e&lKit Normal");
            this.plugin.getPlayerManager().setDefaultKit(player);
        } else {
            this.plugin.getMessagesController().sendMessage(player, "&c&lModificaciones: &e&lKit BuildUHC");
            this.plugin.getPlayerManager().setUHCKit(player);
        }
    }

    public void init() {
        this._enchanterSkill.clear();
        this._wizardSkill.clear();
        this._naturalRegeneration.clear();
        this._uhcRegeneration.clear();
        this.kitbuilduhc.clear();
        this.kitnormal.clear();
    }

    public void addVoteToNormal(Player player) {
        if (this._naturalRegeneration.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por esta opcion.");
        } else if (this._uhcRegeneration.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por una opcion.");
        } else {
            this._naturalRegeneration.add(player);
            this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&lRegeneracion normal");
            this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &eregeneracion normal&7.");
            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&6" + player.getName() + " &evoto por", "&eRegeneracion Normal", 0, 0, 0);
                    }
            );
        }
    }

    public void addVoteToUHC(Player player) {
        if (this._uhcRegeneration.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por esta opcion.");
        } else if (this._naturalRegeneration.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por una opcion.");
        } else {
            this._uhcRegeneration.add(player);
            this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&lRegeneracion UHC");
            this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &eregeneracion UHC&7.");
            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&6" + player.getName() + " &evoto por", "&eRegeneracion UHC", 0, 0, 0);
                    }
            );
        }
    }

    public void addVoteToKitBuildUHC(Player player) {
        if (this.kitbuilduhc.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por esta opcion.");
        } else if (this.kitnormal.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por una opcion.");
        } else {
            this.kitbuilduhc.add(player);
            this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&lKit BuildUHC");
            this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &eKit BuildUHC.");
            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&6" + player.getName() + " &evoto por", "&eKit BuildUHC", 0, 0, 0);
                    }
            );
        }
    }

    public void addVoteToKitDefault(Player player) {
        if (this.kitnormal.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por esta opcion.");
        } else if (this.kitbuilduhc.contains((Object) player)) {
            this.plugin.getMessagesController().sendMessage(player, "&cYa votaste por una opcion.");
        } else {
            this.kitnormal.add(player);
            this.plugin.getMessagesController().sendMessage(player, "&cHas votado por &e&lKit normal");
            this.plugin.getMessagesController().sendBroadcast("&6" + player.getDisplayName() + " &7voto por &ekit normal&7.");
            this.plugin.getGameManager().getPlayers().stream().forEach(players -> {
                        this.plugin.getMessagesController().sendTitle(players, "&6" + player.getName() + " &evoto por", "&eKit Normal", 0, 0, 0);
                    }
            );
        }
    }

    public void setReward(PlayerDeathEvent e) {
        if (this.plugin.getGameManager().isInGame() && e.getEntity().getKiller() instanceof Player) {
            if (this._enchanterSkill.contains((Object) e.getEntity().getKiller())) {
                int intran = this.rand.nextInt(10);
                if (intran <= 5) {
                    e.getEntity().getKiller().giveExp(intran);
                    this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                this.plugin.getMessagesController().sendActionBar(e.getEntity().getKiller(), "&6+" + intran + " &7de XP");
                            }
                    );
                } else {
                    e.getEntity().getKiller().giveExp(intran);
                    this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                this.plugin.getMessagesController().sendActionBar(e.getEntity().getKiller(), "&6+" + intran + " &7de XP");
                            }
                    );
                }
            } else if (this._wizardSkill.contains((Object) e.getEntity().getKiller())) {
                int pro = this.rand.nextInt(20);
                if (pro <= 10) {
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack[]{ItemLoader.getEs1()});
                    this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                this.plugin.getMessagesController().sendActionBar(e.getEntity().getKiller(), "&6+1 &7libro de agudeza");
                            }
                    );
                } else {
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack[]{ItemLoader.getEs1()});
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack[]{ItemLoader.getGoldenApple()});
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack[]{ItemLoader.getPr1()});
                    this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                                this.plugin.getMessagesController().sendActionBar(e.getEntity().getKiller(), "&6+1 &7libro de agudeza, golden apple, pocion de regeneracion");
                            }
                    );
                }
            }
        }
    }

    public void addPlayerToWizardSkill(Player player) {
        if (this._enchanterSkill.contains((Object) player) || this._wizardSkill.contains((Object) player)) {
            this._enchanterSkill.remove((Object) player);
            this._wizardSkill.remove((Object) player);
            this._wizardSkill.add(player);
        } else {
            this._wizardSkill.add(player);
        }
    }

    public void addPlayerToEnchanterSkill(Player player) {
        if (this._enchanterSkill.contains((Object) player) || this._wizardSkill.contains((Object) player)) {
            this._enchanterSkill.remove((Object) player);
            this._wizardSkill.remove((Object) player);
            this._enchanterSkill.add(player);
        } else {
            this._enchanterSkill.add(player);
        }
    }

    public ArrayList<Player> getNaturalRegeneration() {
        return this._naturalRegeneration;
    }

    public ArrayList<Player> getUhcRegeneration() {
        return this._uhcRegeneration;
    }

    public ArrayList<Player> getkitbuiluhc() {
        return this.kitbuilduhc;
    }

    public ArrayList<Player> getkitnormal() {
        return this.kitnormal;
    }
}

