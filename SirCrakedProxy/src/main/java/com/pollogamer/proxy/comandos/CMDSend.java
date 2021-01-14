package com.pollogamer.proxy.comandos;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CMDSend extends Command implements TabExecutor {

    public CMDSend() {
        super("send", "bungeecord.command.send");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§4§lSir§1§lCraked §7» §aUtiliza /send <servidor|jugador|all|current> <Destino>");
            return;
        }

        ServerInfo target = ProxyServer.getInstance().getServerInfo(args[1]);
        if (target == null) {
            sender.sendMessage(ProxyServer.getInstance().getTranslation("no_server", new Object[0]));
            return;
        }

        Iterator localIterator;
        ProxiedPlayer p;
        if (args[0].equalsIgnoreCase("all")) {
            for (localIterator = ProxyServer.getInstance().getPlayers().iterator(); localIterator.hasNext(); ) {
                p = (ProxiedPlayer) localIterator.next();
                summon(p, target, sender);
            }
        } else if (args[0].equalsIgnoreCase("current")) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage("§4§lSir§1§lCraked §7» §Solo los jugadores pueden estar en juego");
                return;
            }

            ProxiedPlayer player = (ProxiedPlayer) sender;
            for (ProxiedPlayer all : player.getServer().getInfo().getPlayers()) {
                summon(all, target, sender);
            }
        } else {
            ServerInfo serverTarget = ProxyServer.getInstance().getServerInfo(args[0]);
            if (serverTarget != null) {
                for (ProxiedPlayer all2 : serverTarget.getPlayers()) {
                    summon(all2, target, sender);
                }
            } else {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage("§4§lSir§1§lCraked §7» §aEl jugador " + args[0] + " no esta conectado");
                    return;
                }
                summon(player, target, sender);
            }
        }

        sender.sendMessage(ChatColor.GREEN + "§4§lSir§1§lCraked §7» §aLos jugadores fueron mandados correctamente");
    }

    private void summon(ProxiedPlayer player, ServerInfo target, CommandSender sender) {
        if ((player.getServer() != null) && (!player.getServer().getInfo().equals(target))) {
            player.connect(target);
            player.sendMessage("§4§lSir§1§lCraked §7» §aFuiste mandado al servidor " + target.getName() + " por " + sender.getName());
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if ((args.length > 2) || (args.length == 0)) {
            return ImmutableSet.of();
        }

        Set<String> matches = new HashSet<>();
        String search;

        if (args.length == 1) {
            String search2 = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search2)) {
                    matches.add(player.getName());
                }
            }

            if ("all".startsWith(search2)) {
                matches.add("all");
            }

            if ("current".startsWith(search2)) {
                matches.add("current");
            }
        } else {
            search = args[1].toLowerCase();
            for (String server : ProxyServer.getInstance().getServers().keySet()) {
                if (server.toLowerCase().startsWith(search)) {
                    matches.add(server);
                }
            }
        }
        return matches;
    }
}
