package com.pollogamer.sircrakedserver.comandos;

import com.pollogamer.sircrakedserver.listener.PlayerListener;
import com.pollogamer.sircrakedserver.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo los jugadores pueden usar este comando");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.chat")) {
            p.sendMessage(Lang.prefix + "No tienes permisos para utilizar este comando!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(Lang.prefix + "Utiliza /chat clear|toggle");
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "clear":
                    for (int i = 0; i < 140; i++) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (!all.hasPermission("sircraked.chat.bypass")) {
                                all.sendMessage("");
                            }
                        }
                    }
                    Bukkit.broadcastMessage(Lang.prefix + "Chat limpiado por " + p.getName());
                    break;
                case "toggle":
                    toggleChat(p);
                    break;
                default:
                    p.sendMessage(Lang.prefix + "Utiliza /chat clear|toggle");
                    break;
            }
        } else {
            p.sendMessage(Lang.prefix + "Utiliza /chat clear|toggle");
        }
        return true;
    }

    public boolean isMuted() {
        return PlayerListener.chat;
    }

    public void toggleChat(Player player) {
        if (isMuted()) {
            unMuteChat(player);
        } else {
            muteChat(player);
        }
    }

    public void muteChat(Player player) {
        PlayerListener.chat = true;
        PlayerListener.chatmutedby = player.getName();
        Bukkit.broadcastMessage(Lang.prefix + "El chat fue muteado por " + PlayerListener.chatmutedby + "!");
    }

    public void unMuteChat(Player player) {
        PlayerListener.chat = false;
        Bukkit.broadcastMessage(Lang.prefix + "El chat fue desmuteado por " + player.getName());
    }
}
