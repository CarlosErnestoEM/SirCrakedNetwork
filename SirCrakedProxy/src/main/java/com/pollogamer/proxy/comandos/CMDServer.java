package com.pollogamer.proxy.comandos;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;
import java.util.Map;

public class CMDServer extends Command implements TabExecutor {

    public CMDServer() {
        super("servidor", "bungeecord.command.server");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

        if (args.length == 0) {
            player.sendMessage(ProxyServer.getInstance().getTranslation("current_server", new Object[]{player.getServer().getInfo().getName()}));
        } else {
            ServerInfo server = servers.get(args[0]);
            if (server == null) {
                player.sendMessage(ProxyServer.getInstance().getTranslation("no_server", new Object[0]));
            } else if (!server.canAccess(player)) {
                player.sendMessage(ProxyServer.getInstance().getTranslation("no_server_permission", new Object[0]));
            } else {
                player.connect(server);
            }
        }
    }

    public Iterable<String> onTabComplete(final CommandSender sender, final String[] args) {
        return (Iterable<String>) ((args.length > 1) ? Collections.EMPTY_LIST : Iterables.transform(Iterables.filter(ProxyServer.getInstance().getServers().values(), new Predicate<ServerInfo>() {
            private final String lower = (args.length == 0) ? "" : args[0].toLowerCase();

            public boolean apply(ServerInfo input) {
                return input.getName().toLowerCase().startsWith(this.lower) && input.canAccess(sender);
            }
        }), new Function<ServerInfo, String>() {
            public String apply(ServerInfo input) {
                return input.getName();
            }
        }));
    }
}

