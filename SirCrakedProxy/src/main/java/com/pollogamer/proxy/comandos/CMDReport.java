package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.UUID;

public class CMDReport extends Command {

    public CMDReport() {
        super("report");
    }

    private HashMap<UUID, Long> time = new HashMap<>();
    private int cooldown = 120000;
    private Principal plugin;

    public void execute(CommandSender cs, String[] args) {
        if ((cs instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) cs;
            if (!p.hasPermission("sircraked.report.use")) {
                p.sendMessage(new TextComponent(Principal.prefix + "No tienes permiso para hacer eso"));
                return;
            }

            if (args.length >= 2) {
                ProxiedPlayer obj = BungeeCord.getInstance().getPlayer(args[0]);
                if ((obj != null) && (obj.isConnected())) {
                    String msg;
                    String server;
                    if (canREPORT(p.getUniqueId())) {
                        this.time.put(p.getUniqueId(), System.currentTimeMillis());
                        msg = "";
                        for (int i = 1; i < args.length; i++) {
                            msg = msg + args[i] + " ";
                        }
                        if (!msg.isEmpty()) {
                            msg = msg.substring(0, msg.length() - 1);
                        }
                        server = obj.getServer().getInfo().getName();
                        p.sendMessage(new TextComponent(Principal.prefix + "El reporte ha sido enviado!"));
                        for (ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
                            if (pl.hasPermission("sircraked.report.see")) {
                                pl.sendMessage(new TextComponent("§8§l§m-------------------------------"));
                                pl.sendMessage(new TextComponent("§7Reportante: §6" + p.getName()));
                                pl.sendMessage(new TextComponent("§7Usuario Reportado: §c" + obj.getName()));
                                pl.sendMessage(new TextComponent("§7Servidor: §c" + server));
                                pl.sendMessage(new TextComponent("§7Razon: §c" + msg));
                                pl.sendMessage(new TextComponent(""));
                                TextComponent st = new TextComponent("§c§lClickeame para ir a ese servidor");
                                st.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClickeame para ir al servidor " + server).create()));
                                st.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/servidor " + server));
                                pl.sendMessage(st);
                                pl.sendMessage(new TextComponent("§8§l§m-------------------------------"));
                            }
                        }
                    } else {
                        double timeleft = Math.round((float) ((this.time.get(p.getUniqueId()) + this.cooldown - System.currentTimeMillis()) / 1000L * 100L));
                        p.sendMessage(new TextComponent(Principal.prefix + "Para poder reportar denuevo espera " + String.valueOf(timeleft / 100.0D) + " segundos"));
                    }
                } else {
                    p.sendMessage(new TextComponent(Principal.prefix + "El jugador " + args[0] + " no esta conectado!"));
                }
            } else {
                p.sendMessage(new TextComponent(Principal.prefix + "Utiliza /report <Jugador> <Razon>"));
            }
        }
    }

    private boolean canREPORT(UUID uuid) {
        if (this.time.containsKey(uuid)) {
            long current = System.currentTimeMillis();
            return this.time.get(uuid) + this.cooldown <= current;
        }
        return true;
    }
}
