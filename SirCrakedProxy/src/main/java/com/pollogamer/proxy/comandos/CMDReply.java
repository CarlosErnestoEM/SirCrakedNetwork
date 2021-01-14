package com.pollogamer.proxy.comandos;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDReply extends Command {
    public CMDReply() {
        super("reply", "", "r", "responder");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            if (!CMDMSG.lastMessage.containsKey(sender.getName())) {
                sender.sendMessage(new TextComponent("§4§lSir§1§lCraked §7» §aNo tienes a quien responderle!"));
                return;
            }

            ProxiedPlayer obj = ProxyServer.getInstance().getPlayer((String) CMDMSG.lastMessage.get(sender.getName()));
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (obj == null) {
                sender.sendMessage(new TextComponent("§4§lSir§1§lCraked §7» §a" + (String) CMDMSG.lastMessage.get(sender.getName()) + " no esta conectado!"));
                return;
            }

            if (CMDMSG.blockmsg.contains(obj)) {
                p.sendMessage("§4§lSir§1§lCraked §7» §aNo le puedes mandar mensajes a " + obj.getDisplayName() + " porque te bloqueo!");
                return;
            }

            String message = "";
            for (String arg : args) {
                message = message + arg + " ";
            }

            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message.trim()));

            obj.sendMessage(new TextComponent("§7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + message));
            p.sendMessage(new TextComponent("§7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + message));
            for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                if (CMDMSG.socialspy.contains(all)) {
                    all.sendMessage(new TextComponent("§6§lSPY  §7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + message));
                }
            }

            if (CMDMSG.lastMessage.containsKey(sender.getName())) {
                CMDMSG.lastMessage.remove(sender.getName());
            }

            CMDMSG.lastMessage.put(sender.getName(), obj.getName());
            if (CMDMSG.lastMessage.containsKey(obj.getName())) {
                CMDMSG.lastMessage.remove(obj.getName());
            }

            CMDMSG.lastMessage.put(obj.getName(), sender.getName());
        } else {
            sender.sendMessage(new TextComponent("§4§lSir§1§lCraked §7» §aUtiliza /r (mensaje)"));
        }
    }
}
