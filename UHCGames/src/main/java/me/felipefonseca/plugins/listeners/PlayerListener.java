package me.felipefonseca.plugins.listeners;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.GameState;
import me.felipefonseca.plugins.task.LobbyCountdown;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent playerLoginEvent) {
        Player player = playerLoginEvent.getPlayer();
        if (this.plugin.getGameManager().isWaiting() && this.plugin.getGameManager().getPlayersInGame().size() < this.plugin.getArenaManager().getMaxPlayers()) {
            playerLoginEvent.allow();
        } else if (this.plugin.getGameManager().isFinished() || this.plugin.getGameManager().isInGame()) {
            if (player.hasPermission("UHCGames.Join")) {
                playerLoginEvent.allow();
            } else {
                playerLoginEvent.setResult(PlayerLoginEvent.Result.KICK_FULL);
                playerLoginEvent.setKickMessage("La arena esta en juego!");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        playerJoinEvent.setJoinMessage(null);
        if (!this.plugin.getGameManager().isWaiting() || this.plugin.getGameManager().getPlayersInGame().size() >= this.plugin.getArenaManager().getMaxPlayers()) {
            this.plugin.getPlayerManager().setSpectator(player);
        } else {
            player.teleport(this.plugin.getArenaManager().getLobby());
            this.plugin.getPlayerManager().setLobbyPlayer(player);
            plugin.getMessageSender().sendBroadcast("&7" + player.getName() + " &6entro al desvergue! " + this.plugin.getGameManager().getPlayersInGame().size() + "/" + this.plugin.getArenaManager().getMaxPlayers());
            if (this.plugin.getGameManager().getPlayersInGame().size() == this.plugin.getArenaManager().getMinPlayers() && !this.plugin.getGameManager().start) {
                new LobbyCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
                this.plugin.getGameManager().start = true;
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        if (this.plugin.getGameManager().isWaiting()) {
            this.plugin.getGameManager().removePlayerFromGame(player);
            plugin.getMessageSender().sendBroadcast("&6" + player.getName() + " &asalio al desvergue! " + this.plugin.getGameManager().getPlayersInGame().size() + "/" + this.plugin.getArenaManager().getMaxPlayers());
        } else if (this.plugin.getGameManager().isInGame()) {
            this.plugin.getGameManager().removePlayerFromGame(player);
            if (this.plugin.getGameManager().getPlayersInGame().contains(event.getPlayer())) {
                plugin.getMessageSender().sendBroadcast("&6" + player.getName() + " &asalio del desvergue! Quedan " + plugin.getGameManager().getPlayersInGame().size() + " jugadores en juego!");
            }
            this.plugin.getGameManager().checkWinner();
            this.plugin.getGameManager().checkDm();
        } else if (this.plugin.getGameManager().isFinished()) {

        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (asyncPlayerChatEvent.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            if (asyncPlayerChatEvent.getPlayer().hasPermission("UHCGames.Admin")) {
                asyncPlayerChatEvent.setFormat(ChatColor.GOLD + asyncPlayerChatEvent.getPlayer().getDisplayName() + (Object) ChatColor.WHITE + ": " + asyncPlayerChatEvent.getMessage());
            } else {
                asyncPlayerChatEvent.setCancelled(true);
            }
        }
        if (asyncPlayerChatEvent.getPlayer().hasPermission("UHCGames.Admin")) {
            asyncPlayerChatEvent.setFormat((Object) ChatColor.GOLD + asyncPlayerChatEvent.getPlayer().getDisplayName() + (Object) ChatColor.WHITE + ": " + asyncPlayerChatEvent.getMessage());
        } else {
            asyncPlayerChatEvent.setFormat((Object) ChatColor.GREEN + asyncPlayerChatEvent.getPlayer().getDisplayName() + (Object) ChatColor.WHITE + ": " + (Object) ChatColor.GRAY + asyncPlayerChatEvent.getMessage());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        if (GameState.State.state == GameState.State.PPREGAME && (playerMoveEvent.getFrom().getBlockZ() != playerMoveEvent.getTo().getBlockZ() || playerMoveEvent.getFrom().getBlockX() != playerMoveEvent.getTo().getBlockX())) {
            playerMoveEvent.setTo(playerMoveEvent.getFrom());
        }
    }

    public void registerPlayerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

}

