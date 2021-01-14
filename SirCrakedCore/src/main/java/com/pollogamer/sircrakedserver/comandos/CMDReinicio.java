package com.pollogamer.sircrakedserver.comandos;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDReinicio implements CommandExecutor {

    private int segundos;
    private List<Integer> numeros = new ArrayList<>(Arrays.asList(new Integer[]{60, 30, 10}));

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("sircraked.reinicio")) {
            sender.sendMessage(Lang.prefix + "No tienes permiso para ejecutar este comando!");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(Lang.prefix + "Utiliza /reinicio <Tiempo>");
            return true;
        } else if (args.length > 1) {
            sender.sendMessage(Lang.prefix + "Utiliza /reinicio <Tiempo>");
            return true;
        } else if (args.length == 1) {
            try {
                this.segundos = Integer.parseInt(args[0]);
            } catch (Exception e) {
                sender.sendMessage(Lang.prefix + "Por favor ingresa un numero valido.");
                return true;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (CMDReinicio.this.numeros.contains(segundos)) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            MessagesController.sendTitle(p, "§c§lREINICIO EN", "§a" + segundos + " segundos!", 10, 20, 10);
                        }
                        Bukkit.getServer().broadcastMessage("§4§lSir§1§lCraked §7» §aReinicio en §c" + CMDReinicio.this.segundos);
                    }
                    if (segundos <= 5 && segundos > 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            MessagesController.sendTitle(p, "§c§lREINICIO EN", "§a" + segundos + " segundos!", 10, 20, 10);
                        }
                    }
                    if (segundos == -3) {
                        Bukkit.shutdown();
                    } else if (segundos == -1) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.kickPlayer(Lang.prefix + "Servidor reiniciado!");
                        }
                    }
                    --segundos;
                }
            }.runTaskTimer(SirCrakedCore.getCore(), 0, 20);
            return true;
        }
        return true;
    }

}
