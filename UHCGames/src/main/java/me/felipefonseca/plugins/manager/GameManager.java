package me.felipefonseca.plugins.manager;

import me.felipefonseca.plugins.Main;
import me.felipefonseca.plugins.task.DeathMatchCountdown;
import me.felipefonseca.plugins.task.WinnerCountdown;
import me.felipefonseca.plugins.utils.ItemBuilder;
import me.felipefonseca.plugins.utils.MessagesLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameManager {

    public Player winner;
    private final Main plugin;
    private final ArrayList<Player> PLAYERS_IN_GAME;
    private final ArrayList<Player> SPECTATORS;
    private ItemStack MANZANA;
    public boolean start;
    public boolean dm;

    public GameManager(Main main) {
        this.plugin = main;
        this.PLAYERS_IN_GAME = new ArrayList();
        this.SPECTATORS = new ArrayList();
    }

    public void init() {
        this.PLAYERS_IN_GAME.clear();
        this.SPECTATORS.clear();
        this.MANZANA = new ItemBuilder(Material.GOLDEN_APPLE).setDisplayName(MessagesLoader.getGoldenHead()).build();
        this.start = false;
        this.dm = false;
    }

    public void checkDm() {
        if (this.PLAYERS_IN_GAME.size() <= 2 && !this.dm) {
            this.dm = true;
            this.plugin.getArenaManager().loadSpawn();
            GameState.state = GameState.DEATHMATCH;
            new DeathMatchCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
        } else if (this.PLAYERS_IN_GAME.size() == 1 || this.PLAYERS_IN_GAME.isEmpty()) {
            this.plugin.getServer().shutdown();
        }
    }

    public void checkWinner() {
        if (this.isInGame() && this.PLAYERS_IN_GAME.size() < 2) {
            this.PLAYERS_IN_GAME.stream().forEach(player -> {
                        this.plugin.getMessageSender().sendBroadcast("§e" + player.getName() + " gano el juego!");
                        winner = player;
                    }
            );
            new WinnerCountdown(this.plugin).runTaskTimer(this.plugin, 0, 20);
            GameState.state = GameState.ENDING;
        }
    }

    public void addPlayerToGame(Player player) {
        if (this.PLAYERS_IN_GAME.contains((Object) player)) {
            this.PLAYERS_IN_GAME.remove((Object) player);
            this.PLAYERS_IN_GAME.add(player);
        } else {
            this.PLAYERS_IN_GAME.add(player);
        }
    }

    public boolean isNotPVP() {
        return GameState.State.state == GameState.State.NO_PVP;
    }

    public boolean isInGame() {
        return GameState.state == GameState.IN_GAME || GameState.state == GameState.DEATHMATCH;
    }

    public boolean isWaiting() {
        return GameState.state == GameState.WAITING;
    }

    public boolean isFinished() {
        return GameState.state == GameState.ENDING;
    }

    public boolean isPreparing() {
        return GameState.State.state == GameState.State.PPREGAME;
    }

    public boolean isInEditMode() {
        return this.plugin.getEditMode();
    }

    public void removePlayerFromGame(Player player) {
        if (this.PLAYERS_IN_GAME.contains(player)) {
            this.PLAYERS_IN_GAME.remove(player);
        }
    }

    public boolean isPlayerinGame(Player player) {
        return this.PLAYERS_IN_GAME.contains((Object) player);
    }

    public ArrayList<Player> getPlayersInGame() {
        return this.PLAYERS_IN_GAME;
    }

    public ArrayList<Player> getSpectators() {
        return this.SPECTATORS;
    }

    public ItemStack getManzana() {
        return this.MANZANA;
    }

    public boolean isStart() {
        return this.start;
    }
}

