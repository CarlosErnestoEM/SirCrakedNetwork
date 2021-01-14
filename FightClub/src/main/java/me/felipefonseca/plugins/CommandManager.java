/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package me.felipefonseca.plugins;

import me.felipefonseca.plugins.utils.Tools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager
        implements CommandExecutor {
    private final Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.plugin.getCommand("game").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (cmd.getName().equalsIgnoreCase("game")) {
            if (player == null) {
                this.plugin.getMessagesController().sendMessageToSender(sender, "&c\u00a1Necesitas ser un jugador!");
            } else if (player.hasPermission("FightClub.Admin.Setup")) {
                if (args.length < 1) {
                    this.plugin.getMessagesController().sendMessageToSender(sender, "&7Usa &e/game setlobby/setmin/setmax/setspawn/setdmspawn/start");
                } else {
                    switch (args[0].toLowerCase()) {
                        case "setlobby": {
                            this.plugin.getArenaConfiguration().getArenaConfig().set("lobby", (Object) Tools.locationToString(player.getLocation()));
                            this.plugin.getArenaConfiguration().save();
                            this.plugin.getMessagesController().sendMessageToSender(sender, "&7El &a&lLOBBY &7ser\u00e1 en " + Tools.locationToString(player.getLocation()));
                            break;
                        }
                        case "setmin": {
                            if (args.length < 1) {
                                this.plugin.getMessagesController().sendMessageToSender(sender, "67Usa &e/game setmin CANTIDAD");
                                break;
                            }
                            int minPlayers = Integer.valueOf(args[1]);
                            this.plugin.getArenaConfiguration().getArenaConfig().set("min", (Object) minPlayers);
                            this.plugin.getArenaConfiguration().save();
                            this.plugin.getMessagesController().sendMessageToSender(sender, "&7El m\u00ednimo de jugadores ser\u00e1 de &a&l" + minPlayers);
                            break;
                        }
                        case "setmax": {
                            if (args.length < 1) {
                                this.plugin.getMessagesController().sendMessageToSender(sender, "67Usa &e/game setmax CANTIDAD");
                                break;
                            }
                            int maxPlayers = Integer.valueOf(args[1]);
                            this.plugin.getArenaConfiguration().getArenaConfig().set("max", (Object) maxPlayers);
                            this.plugin.getArenaConfiguration().save();
                            this.plugin.getMessagesController().sendMessageToSender(sender, "&7El m\u00e1ximo de jugadores ser\u00e1 de &a&l" + maxPlayers);
                            break;
                        }
                        case "setspawn": {
                            this.plugin.getArenaManager().setSpawnLocation(player);
                            break;
                        }
                        case "setdmspawn": {
                            this.plugin.getArenaManager().setDMSpawnLocation(player);
                            break;
                        }
                        case "start": {
                            this.plugin.getGameManager().forceStart();
                        }
                    }
                }
            } else {
                this.plugin.getMessagesController().sendMessageToSender(sender, "&c\u00a1No puedes hacer \u00e9sto!");
            }
        }
        return false;
    }
}

