package com.pollogamer.sircrakedserver.npc;

import com.mojang.authlib.GameProfile;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.npc.events.NPCDespawnEvent;
import com.pollogamer.sircrakedserver.npc.events.NPCSpawnEvent;
import com.pollogamer.sircrakedserver.utils.GameProfileUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class NPC {

    private PacketPlayOutScoreboardTeam scbpacket;
    private Location location;
    private int npcID;
    private int entityID;
    private GameProfile gameProfile;
    private List<Player> rendered = new ArrayList<>();

    public NPC(Location location, String skin) {
        entityID = (int) Math.ceil(Math.random() * 1000) + 2000;
        npcID = entityID;
        gameProfile = GameProfileUtils.getGameProfileFromName(skin, getRandomString(8));
        this.location = location;
    }

    private void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
        }
    }

    private Object getValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
        }
        return null;
    }

    private void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private String getRandomString(int lenght) {
        String randStr = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < lenght) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                randStr += c;
                i++;
            }
        }
        return randStr;
    }

    public NPC delete() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            destroy(p);
        }
        return this;
    }

    public void spawn(Player p) {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

        setValue(packet, "a", entityID);
        setValue(packet, "b", gameProfile.getId());
        setValue(packet, "c", (int) MathHelper.floor(location.getX() * 32.0D));
        setValue(packet, "d", (int) MathHelper.floor(location.getY() * 32.0D));
        setValue(packet, "e", (int) MathHelper.floor(location.getZ() * 32.0D));
        setValue(packet, "f", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
        setValue(packet, "g", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));
        DataWatcher w = new DataWatcher(null);
        w.a(10, (byte) 127);
        setValue(packet, "i", w);
        try {
            scbpacket = new PacketPlayOutScoreboardTeam();
            setValue(scbpacket, "h", 0);
            setValue(scbpacket, "b", gameProfile.getName());
            setValue(scbpacket, "a", gameProfile.getName());
            setValue(scbpacket, "e", "never");
            setValue(scbpacket, "i", 1);
            Field f = scbpacket.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((Collection) f.get(scbpacket)).add(gameProfile.getName());
            sendPacket(scbpacket, p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addToTablist(p);
        sendPacket(packet, p);
        PacketPlayOutEntityHeadRotation rotationpacket = new PacketPlayOutEntityHeadRotation();
        setValue(rotationpacket, "a", entityID);
        setValue(rotationpacket, "b", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
        sendPacket(rotationpacket, p);
        new BukkitRunnable() {
            @Override
            public void run() {
                rmvFromTablist(p);
            }
        }.runTaskLater(SirCrakedCore.getCore(), 26);
        rendered.add(p);
        NPCSpawnEvent event = new NPCSpawnEvent(p, this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void destroy(Player p) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{entityID});
        rmvFromTablist(p);
        sendPacket(packet, p);
        try {
            PacketPlayOutScoreboardTeam removescbpacket = new PacketPlayOutScoreboardTeam();
            Field f = removescbpacket.getClass().getDeclaredField("a");
            f.setAccessible(true);
            f.set(removescbpacket, gameProfile.getName());
            f.setAccessible(false);
            Field f2 = removescbpacket.getClass().getDeclaredField("h");
            f2.setAccessible(true);
            f2.set(removescbpacket, 1);
            f2.setAccessible(false);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(removescbpacket);
            rendered.remove(p);
            NPCDespawnEvent event = new NPCDespawnEvent(p, this);
            Bukkit.getPluginManager().callEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addToTablist(Player p) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameProfile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString("§8[NPC] " + gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet, p);
    }

    private void rmvFromTablist(Player p) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameProfile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString("§8[NPC] " + gameProfile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet, p);
    }


    public Location getLocation() {
        return this.location;
    }

    public int getEntityID() {
        return this.entityID;
    }

    public int getNpcID() {
        return npcID;
    }

    public PacketPlayOutScoreboardTeam getScbpacket() {
        return scbpacket;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public List<Player> getRendered() {
        return rendered;
    }
}

