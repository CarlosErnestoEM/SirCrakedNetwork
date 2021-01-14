/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutChat
 *  net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle$EnumTitleAction
 *  net.minecraft.server.v1_8_R3.PlayerConnection
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package me.felipefonseca.plugins.utils;

import me.felipefonseca.plugins.Main;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class MessagesController {
    private final Main plugin;
    private String prefix;
    private static final int CENTER_PX = 154;

    public MessagesController(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.prefix = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) this.plugin.getConfig().getString("prefix"));
    }

    public void sendMessage(Player player, String msg) {
        player.sendMessage(this.prefix + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg));
    }

    public void sendMessageToSender(CommandSender sender, String msg) {
        sender.sendMessage(this.prefix + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg));
    }

    public void sendColorMessage(Player player, String msg) {
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg));
    }

    public void sendColorMessageToSender(CommandSender sender, String msg) {
        sender.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg));
    }

    public void sendBroadcast(String msg) {
        this.plugin.getServer().broadcastMessage(this.prefix + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg));
    }

    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a((String) ("{'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) title) + "'}"));
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a((String) ("{'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) subtitle) + "'}"));
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket((Packet) titlePacket);
        connection.sendPacket((Packet) subtitlePacket);
    }

    public void sendActionBar(Player p, String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) msg) + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) ppoc);
    }

    public void sendHeaderAndFooter(Player p, String head, String foot) {
        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a((String) ("{'color': '', 'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) head) + "'}"));
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a((String) ("{'color': '', 'text': '" + net.md_5.bungee.api.ChatColor.translateAlternateColorCodes((char) '&', (String) foot) + "'}"));
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set((Object) packet, (Object) header);
            headerField.setAccessible(!headerField.isAccessible());
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set((Object) packet, (Object) footer);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException headerField) {
            // empty catch block
        }
        connection.sendPacket((Packet) packet);
    }

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) {
            player.sendMessage("");
        }
        message = ChatColor.translateAlternateColorCodes((char) '&', (String) message);
        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;
        for (char c : message.toCharArray()) {
            if (c == '\u00a7') {
                previousCode = true;
                continue;
            }
            if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                }
                isBold = false;
                continue;
            }
            DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
            ++messagePxSize;
        }
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        StringBuilder sb = new StringBuilder();
        for (int compensated = 0; compensated < toCompensate; compensated += spaceLength) {
            sb.append(" ");
        }
        player.sendMessage(sb.toString() + message);
    }

    public String getPrefix() {
        return this.prefix;
    }
}

