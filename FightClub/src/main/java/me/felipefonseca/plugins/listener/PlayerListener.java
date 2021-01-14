/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerLoginEvent$Result
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  ru.tehkode.permissions.PermissionUser
 *  ru.tehkode.permissions.bukkit.PermissionsEx
 */
package me.felipefonseca.plugins.listener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.task.LobbyTask;
import me.felipefonseca.plugins.utils.ItemLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerListener
        implements Listener {
    private final Main plugin;

    PowerfulPermsPlugin pp;
    PermissionManager permissionManager;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
        pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
        permissionManager = pp.getPermissionManager();
    }


    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (this.plugin.getGameManager().isInLobby() && this.plugin.getGameManager().getPlayers().size() < this.plugin.getArenaManager().getMaxPlayers()) {
            e.allow();
        } else if (e.getPlayer().hasPermission("FightClub.ByPass") && this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch() || this.plugin.getGameManager().isEnding()) {
            e.allow();
        } else {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "\u00a7cJuego en progreso");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);
        if (this.plugin.getGameManager().isInLobby() && this.plugin.getGameManager().getPlayers().size() < this.plugin.getArenaManager().getMaxPlayers()) {
            this.plugin.getGameManager().addPlayerToGame(player);
            this.plugin.getPlayerManager().setLobbyPlayer(player);
            this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                        this.plugin.getMessagesController().sendBroadcast("&7Ha entrado al desvergue &e" + player.getDisplayName() + " &d(&6" + this.plugin.getGameManager().getPlayers().size() + "&3/&6" + this.plugin.getArenaManager().getMaxPlayers() + "&d)");
                        player.getInventory().addItem(new ItemStack[]{ItemLoader.getSelector()});
                        player.getInventory().addItem(new ItemStack[]{ItemLoader.getGameModificator()});
                        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0f, 1.0f);
                    }
            );
            if (this.plugin.getGameManager().isNotStarted() && this.plugin.getGameManager().getPlayers().size() == this.plugin.getArenaManager().getMinPlayers()) {
                this.plugin.getGameManager().setStarted(true);
                new LobbyTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
            }
        } else {
            this.plugin.getPlayerManager().setSpectator(player);
            this.plugin.getPlayerManager().setupScoreboard(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if (this.plugin.getGameManager().isInLobby()) {
            this.plugin.getGameManager().removePlayerFromGame(e.getPlayer());
            this.plugin.getMessagesController().sendBroadcast("&7Abandono el juego &e" + e.getPlayer().getDisplayName() + " &d(&6" + this.plugin.getGameManager().getPlayers().size() + "&3/&6" + this.plugin.getArenaManager().getMaxPlayers() + "&d)");
        } else if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch()) {
            this.plugin.getGameManager().removePlayerFromGame(e.getPlayer());
            this.plugin.getGameManager().removeSpectator(e.getPlayer());
            this.plugin.getGameManager().removePlayerWithKit(e.getPlayer());
            if (this.plugin.getGameManager().getPlayers().contains((Object) e.getPlayer())) {
                this.plugin.getGameManager().checkWin();
                this.plugin.getGameManager().checkDM();
                this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                            this.plugin.getMessagesController().sendBroadcast("&e" + e.getPlayer().getDisplayName() + " &7ha muerto desconectado.");
                        }
                );
            }
        } else {
            this.plugin.getGameManager().removePlayerFromGame(e.getPlayer());
            this.plugin.getGameManager().removeSpectator(e.getPlayer());
            this.plugin.getGameManager().removePlayerWithKit(e.getPlayer());
        }
        if (this.plugin.getGameManager().isInGame() && this.plugin.getGameManager().getPlayers().isEmpty()) {
            Bukkit.shutdown();
        }
        if (this.plugin.getGameManager().isDeathmatchStarted() && this.plugin.getGameManager().getPlayers().isEmpty()) {
            Bukkit.shutdown();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        if (this.plugin.getGameManager().isInGame() || this.plugin.getGameManager().isInDeathMatch()) {
            if (e.getEntity().getKiller() instanceof Player) {
                this.plugin.getGameManager().removePlayerFromGame(e.getEntity());
                this.plugin.getPlayerManager().setSpectator(e.getEntity());
                this.plugin.getGameManager().removePlayerWithKit(e.getEntity());
                this.plugin.getPlayerManager().addReward(e.getEntity().getKiller());
                this.plugin.getGameManager().addPlayerWithKit(e.getEntity().getKiller());
                this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                            this.plugin.getSkillManager().setReward(e);
                            this.plugin.getMessagesController().sendBroadcast("&e" + e.getEntity().getDisplayName() + " &7fue violado por &e" + e.getEntity().getKiller().getDisplayName() + "&7.");
                        }
                );
            } else {
                this.plugin.getGameManager().removePlayerFromGame(e.getEntity());
                this.plugin.getPlayerManager().setSpectator(e.getEntity());
                this.plugin.getGameManager().removePlayerWithKit(e.getEntity());
                this.plugin.getServer().getScheduler().runTask((Plugin) this.plugin, () -> {
                            this.plugin.getMessagesController().sendBroadcast("&e" + e.getEntity().getDisplayName() + " &7se fue a la puta");
                        }
                );
            }
            this.plugin.getGameManager().checkWin();
            this.plugin.getGameManager().checkDM();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (this.plugin.getGameManager().isInLobby()) {
            if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
                return;
            }
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            Player player = (Player) e.getEntity();
            e.setCancelled(true);
            e.setDamage(0.0);
            player.teleport(this.plugin.getArenaManager().getLobby());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionManager.getPermissionPlayer(p.getUniqueId()).getPrefix());
        if (p.hasPermission("sircraked.color")) {
            event.setFormat(prefix + " §a%1$s§f: §c%2$s");
        } else {
            event.setFormat(prefix + " §a%1$s§f: %2$s");
        }
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this.plugin);
    }

    public void unregisterAllPlayerEvents() {
        HandlerList.unregisterAll((Listener) this);
    }
}

