package com.pollogamer.proxy.comandos;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CMDMSG extends Command {

    public static List<ProxiedPlayer> blockmsg = new ArrayList<>();
    public static List<ProxiedPlayer> socialspy = new ArrayList<>();
    public static HashMap<String, String> lastMessage = new HashMap<>();

    public CMDMSG() {
        super("msg", "", "m", "message", "tell", "t", "w", "mensaje");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(new TextComponent("§4§lSir§1§lCraked §7» §aUtiliza: /msg <jugador> <mensaje>"));
            return;
        }

        ProxiedPlayer obj = ProxyServer.getInstance().getPlayer(args[0]);
        ProxiedPlayer p = (ProxiedPlayer) sender;

        if (obj == null) {
            sender.sendMessage(new TextComponent("§4§lSir§1§lCraked §7» §aEl jugador " + args[0] + " no esta conectado!"));
            return;
        }

        if (obj.getName().equals(p.getName())) {
            p.sendMessage("§4§lSir§1§lCraked §7» §aNo te puedes mandar un mensaje a ti mismo");
            return;
        }

        if (blockmsg.contains(obj)) {
            p.sendMessage("§4§lSir§1§lCraked §7» §aNo le puedes mandar mensajes a " + obj.getDisplayName() + " porque tiene los mensajes desactivados!");
            return;
        }

        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            msgBuilder.append(args[i]).append(" ");
        }

        String msg = ChatColor.translateAlternateColorCodes('&', msgBuilder.toString().trim());
        obj.sendMessage(new TextComponent("§7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + msg));
        p.sendMessage(new TextComponent("§7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + msg));
        for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
            if (socialspy.contains(all)) {
                all.sendMessage(new TextComponent("§6§lSPY  §7[§b" + p.getServer().getInfo().getName() + "§7] §a" + p.getName() + " §b» §7[§b" + obj.getServer().getInfo().getName() + "§7] §a" + obj.getName() + "§f: " + msg));
            }
        }

        if (lastMessage.containsKey(sender.getName())) {
            lastMessage.remove(sender.getName());
        }

        lastMessage.put(sender.getName(), obj.getName());
        if (lastMessage.containsKey(obj.getName())) {
            lastMessage.remove(obj.getName());
        }

        lastMessage.put(obj.getName(), sender.getName());
    }
}
