package com.pollogamer.sircrakedserver.utils;

import com.pollogamer.SirCrakedCore;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MessagesController {

    private static final int CENTER_PX = 154;

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;

        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', title) + "'}");

        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', subtitle) + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);

        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);

        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

    public static void sendActionBar(Player p, String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg) + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }

    public static void sendFullActionBar(Player p, String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg) + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        new BukkitRunnable() {
            public void run() {
                if (p != null) {
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
                } else {
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 0, 40);
    }

    public static void sendCenteredMessage(Player player, String message) {
        if ((message == null) || (message.equals(""))) {
            player.sendMessage("");
            return;
        }

        message = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                if ((c == 'l') || (c == 'L')) {
                    isBold = true;
                } else {
                    isBold = false;
                }
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += (isBold ? dFI.getBoldLength() : dFI.getLength());
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        player.sendMessage(sb.toString() + message);
    }
}
