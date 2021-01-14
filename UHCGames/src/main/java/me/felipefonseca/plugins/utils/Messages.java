package me.felipefonseca.plugins.utils;

import me.felipefonseca.plugins.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Messages {
    private final Main plugin;
    private String prefix;
    private final String ALERT_ICON = "\u26a0";

    public Messages(Main main) {
        this.plugin = main;
    }

    public void init() {
        this.prefix = ChatColor.translateAlternateColorCodes((char) '&', (String) this.plugin.getConfig().getString("prefix"));
    }

    public void sendMessage(Player player, String string) {
        player.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes((char) '&', (String) string));
    }

    public void sendColorMessage(Player player, String string) {
        player.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', (String) string));
    }

    public void sendBroadcast(String string) {
        this.plugin.getServer().broadcastMessage(this.prefix + ChatColor.translateAlternateColorCodes((char) '&', (String) string));
    }

    public void sendAlert(String string) {
        this.plugin.getServer().getOnlinePlayers().stream().map(player -> {
                    if (player.hasPermission("UHCSurvivalGames.Admin")) {
                        this.sendMessage(player, "&e\u26a0 &c" + string);
                    }
                    return player;
                }
        ).forEach(player -> {
                    this.plugin.getLogger().log(Level.INFO, string);
                }
        );
    }

    public void sendTitle(Player player, String string, String string2, int n, int n2, int n3) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a((String) ("{'text': '" + ChatColor.translateAlternateColorCodes((char) '&', (String) string) + "'}"));
        IChatBaseComponent iChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a((String) ("{'text': '" + ChatColor.translateAlternateColorCodes((char) '&', (String) string2) + "'}"));
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent, n, n2, n3);
        PacketPlayOutTitle packetPlayOutTitle2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iChatBaseComponent2);
        playerConnection.sendPacket((Packet) packetPlayOutTitle);
        playerConnection.sendPacket((Packet) packetPlayOutTitle2);
    }

    public void sendActionBar(Player player, String string) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + ChatColor.translateAlternateColorCodes((char) '&', (String) string) + "\"}"));
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet) packetPlayOutChat);
    }

    public void sendHeaderAndFooter(Player player, String string, String string2) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a((String) ("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes((char) '&', (String) string) + "'}"));
        IChatBaseComponent iChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a((String) ("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes((char) '&', (String) string2) + "'}"));
        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field field = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("a");
            field.setAccessible(true);
            field.set((Object) packetPlayOutPlayerListHeaderFooter, (Object) iChatBaseComponent);
            field.setAccessible(!field.isAccessible());
            Field field2 = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("b");
            field2.setAccessible(true);
            field2.set((Object) packetPlayOutPlayerListHeaderFooter, (Object) iChatBaseComponent2);
            field2.setAccessible(!field2.isAccessible());
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException var9_10) {
            // empty catch block
        }
        playerConnection.sendPacket((Packet) packetPlayOutPlayerListHeaderFooter);
    }

    public String getAlertIcon() {
        return "\u26a0";
    }

    public String getPrefix() {
        return this.prefix;
    }
}

