package me.felipefonseca.plugins;

import me.felipefonseca.plugins.task.LobbyCountdown;
import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandManager implements CommandExecutor {

    private final Main plugin;

    public CommandManager(Main main) {
        this.plugin = main;
    }

    public void init() {
        this.plugin.getCommand("game").setExecutor(this);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        if (command.getName().equalsIgnoreCase("game")) {
            if (arrstring.length < 1) {
                this.plugin.getMessageSender().sendMessage(player, "&cStart the config with /game setlobby");
            } else {
                switch (arrstring[0].toLowerCase()) {
                    case "setlobby": {
                        if (player == null) break;
                        this.plugin.getArenaConfiguration().getArenaConfig().set("UHCSG.Arena.Lobby", (Object) Tools.locationToString(player.getLocation()));
                        this.plugin.getArenaConfiguration().save();
                        this.plugin.getMessageSender().sendMessage(player, "&7Now use /game setmin/setmax");
                        break;
                    }
                    case "setmin": {
                        int n = Integer.valueOf(arrstring[1]);
                        if (arrstring.length < 1) {
                            this.plugin.getMessageSender().sendMessage(player, "&cUse /game setmin CANT");
                            break;
                        }
                        this.plugin.getArenaConfiguration().getArenaConfig().set("UHCSG.Arena.Min", (Object) n);
                        this.plugin.getArenaConfiguration().save();
                        this.plugin.getMessageSender().sendMessage(player, "&7Min players &e" + n);
                        break;
                    }
                    case "setmax": {
                        int n = Integer.valueOf(arrstring[1]);
                        if (arrstring.length < 1) {
                            this.plugin.getMessageSender().sendMessage(player, "&cUse /game setmax CANT");
                            break;
                        }
                        this.plugin.getArenaConfiguration().getArenaConfig().set("UHCSG.Arena.Max", (Object) n);
                        this.plugin.getArenaConfiguration().save();
                        this.plugin.getMessageSender().sendMessage(player, "&7Max players &e" + n);
                        this.plugin.getMessageSender().sendMessage(player, "&7Now use /game setspawn");
                        break;
                    }
                    case "setspawn": {
                        if (player == null) break;
                        this.plugin.getArenaManager().addSpawn(player);
                        break;
                    }
                    case "start": {
                        if (this.plugin.getGameManager().isInEditMode()) {
                            this.plugin.getMessageSender().sendAlert("&cServidor en modo editor.");
                            break;
                        }
                        if (!this.plugin.getGameManager().isStart()) {
                            new LobbyCountdown(this.plugin).runTaskTimer((Plugin) this.plugin, 20, 20);
                            this.plugin.getMessageSender().sendBroadcast("&7Esta mamada se forzo a iniciar...");
                            this.plugin.getGameManager().start = true;
                            break;
                        }
                        this.plugin.getMessageSender().sendAlert("&7La partida ya esta iniciada!.");
                        break;
                    }
                    default: {
                        this.plugin.getMessageSender().sendMessage(player, "&cError...");
                    }
                }
            }
        }
        return false;
    }
}

