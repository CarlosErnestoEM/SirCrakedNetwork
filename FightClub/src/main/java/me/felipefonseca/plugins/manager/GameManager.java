/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.manager.enums.GameState;
import me.felipefonseca.plugins.task.DeathMatchTask;
import me.felipefonseca.plugins.task.LobbyTask;
import me.felipefonseca.plugins.task.WinnerTask;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager {
    private final Main plugin;
    private final ArrayList<Player> players;
    private final ArrayList<Player> spectators;
    private final ArrayList<Player> playersWithKit;
    public ReentrantLock _players_lock;
    private final Random rand;
    private boolean started;
    private boolean deathmatchStarted;
    private String winnerName;

    public GameManager(Main plugin) {
        this.plugin = plugin;
        this.players = new ArrayList();
        this.spectators = new ArrayList();
        this.playersWithKit = new ArrayList();
        this.rand = new Random();
        this._players_lock = new ReentrantLock(false);
    }

    public void init() {
        this.players.clear();
        this.spectators.clear();
        this.playersWithKit.clear();
        this.started = false;
        this.deathmatchStarted = false;
        this.winnerName = "";
    }

    public void checkTask() {
        if (this.isInGame()) {
            if (this.players.size() > 2) {
                int random = this.rand.nextInt(this.plugin.getGameManager().getPlayers().size());
                Player player = this.plugin.getGameManager().getPlayers().get(random);
                player.setHealth(0.0);
                this.plugin.getMessagesController().sendBroadcast("&7Ha muerto un jugador para iniciar la deathmatch.");
                this.checkTask();
            } else if (this.players.size() == 2 && !this.deathmatchStarted) {
                GameState.state = GameState.DEATHMATCH;
                this.deathmatchStarted = true;
                new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                this.players.stream().forEach(playersInGame -> {
                            this.plugin.getArenaManager().teleport(playersInGame);
                        }
                );
            } else if (this.isInGame() && this.players.size() == 2 && this.deathmatchStarted) {
                GameState.state = GameState.DEATHMATCH;
                new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                this.players.stream().forEach(playersInGame -> {
                            this.plugin.getArenaManager().teleport(playersInGame);
                        }
                );
            } else {
                GameState.state = GameState.DEATHMATCH;
                new DeathMatchTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                this.players.stream().forEach(playersInGame -> {
                            this.plugin.getArenaManager().teleport(playersInGame);
                        }
                );
            }
        }
    }

    public void checkWin() {
        if (this.getPlayers().size() < 2) {
            for (Player winner : this.getPlayers()) {
                this.winnerName = winner.getDisplayName();
                GameState.state = GameState.ENDING;
                new WinnerTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                this.plugin.getMessagesController().sendBroadcast("&7Ha ganado la partitda &e&l" + winner.getDisplayName());
                this.plugin.getServer().getOnlinePlayers().stream().forEach(playersInGame -> {
                            this.plugin.getMessagesController().sendTitle(playersInGame, "&e&l" + winner.getDisplayName(), "&7gano la partida", 5, 20, 5);
                        }
                );
            }
        }
    }

    public void forceStart() {
        if (!this.started) {
            this.started = true;
            new LobbyTask(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
        }
    }

    public void checkDM() {
        if (!this.deathmatchStarted && this.getPlayers().size() == 2 && this.isInGame()) {
            this.deathmatchStarted = true;
        }
    }

    public void addPlayerToGame(Player player) {
        this._players_lock.lock();
        try {
            this.players.remove((Object) player);
        } finally {
            this.players.add(player);
        }
    }

    public void addSpectator(Player player) {
        this.spectators.remove((Object) player);
        this.spectators.add(player);
    }

    public void addPlayerWithKit(Player player) {
        this.playersWithKit.remove((Object) player);
        this.playersWithKit.add(player);
    }

    public void removePlayerFromGame(Player player) {
        if (this.players.contains((Object) player)) {
            this.players.remove((Object) player);
        }
    }

    public void removeSpectator(Player player) {
        this._players_lock.lock();
        try {
            this.players.remove((Object) player);
        } finally {
            this._players_lock.unlock();
        }
    }

    public void removePlayerWithKit(Player player) {
        this.playersWithKit.remove((Object) player);
    }

    public void setDeathmatchStarted(boolean deathmatchStarted) {
        this.deathmatchStarted = deathmatchStarted;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public boolean isPlayer(Player player) {
        boolean ret;
        this._players_lock.lock();
        try {
            ret = this.players.contains((Object) player);
        } finally {
            this._players_lock.unlock();
        }
        return ret;
    }

    public ArrayList<Player> getSpectators() {
        return this.spectators;
    }

    public ArrayList<Player> getPlayersWithKit() {
        return this.playersWithKit;
    }

    public String getWinnerName() {
        return this.winnerName;
    }

    public boolean isStarted() {
        return this.started;
    }

    public boolean isNotStarted() {
        return !this.started;
    }

    public boolean isDeathmatchStarted() {
        return this.deathmatchStarted;
    }

    public boolean isEnding() {
        return GameState.state == GameState.ENDING;
    }

    public boolean isInDeathMatch() {
        return GameState.state == GameState.DEATHMATCH;
    }

    public boolean isInGame() {
        return GameState.state == GameState.IN_GAME;
    }

    public boolean isInLobby() {
        return GameState.state == GameState.LOBBY;
    }
}

