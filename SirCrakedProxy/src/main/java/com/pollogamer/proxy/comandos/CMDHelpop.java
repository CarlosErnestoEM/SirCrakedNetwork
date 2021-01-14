package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.UUID;

public class CMDHelpop extends Command {

    private final HashMap<UUID, Long> time = new HashMap<>();
    private int cooldown = 120000;
    private Principal plugin;

    public CMDHelpop() {
        super("helpop");
    }

    public void execute(CommandSender cs, String[] args) {
        if ((cs instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) cs;
            if (args.length >= 1) {
                String msg;
                String server;
                if (canHelpop(p.getUniqueId())) {
                    this.time.put(p.getUniqueId(), System.currentTimeMillis());
                    msg = "";
                    for (String arg : args) {
                        msg = msg + arg + " ";
                    }
                    if (!msg.isEmpty()) {
                        msg = msg.substring(0, msg.length() - 1);
                    }
                    server = p.getServer().getInfo().getName();
                    p.sendMessage(new TextComponent(Principal.prefix + "En breve un miembro del staff te ayudara!"));
                    for (ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
                        if (pl.hasPermission("sircraked.report.see")) {
                            pl.sendMessage(new TextComponent("§d[Helpop] §7[§b" + server + "§7]§a " + p.getName() + "§f: §6" + msg));
                        }
                    }
                } else {
                    double timeleft = Math.round((float) ((this.time.get(p.getUniqueId()) + this.cooldown - System.currentTimeMillis()) / 1000L * 100L));
                    p.sendMessage(new TextComponent(Principal.prefix + "Para poder pedir ayuda espera " + String.valueOf(timeleft / 100.0D) + " segundos"));
                }
            } else {
                p.sendMessage(new TextComponent(Principal.prefix + "Utiliza /helpop <Razon>"));
            }
        }
    }

    private boolean canHelpop(UUID uuid) {
        if (this.time.containsKey(uuid)) {
            long current = System.currentTimeMillis();
            return this.time.get(uuid) + this.cooldown <= current;
        }
        return true;
    }
}
