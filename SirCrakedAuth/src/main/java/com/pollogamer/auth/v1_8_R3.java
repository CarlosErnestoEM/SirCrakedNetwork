package com.pollogamer.auth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class v1_8_R3 {
    public v1_8_R3() {
    }

    public static void setLabyModFeature(Player p, HashMap<AuthMeBridgeListener.EnumLabyModFeature, Boolean> list) {
        try {
            HashMap<String, Boolean> nList = new HashMap();
            for (AuthMeBridgeListener.EnumLabyModFeature f : list.keySet()) {
                nList.put(f.name(), (Boolean) list.get(f));
            }
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(nList);
            ByteBuf a = Unpooled.copiedBuffer(byteOut.toByteArray());
            PacketDataSerializer b = new PacketDataSerializer(a);
            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("LABYMOD", b);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.err.println("[AntiLaby/ERROR] An unknown error has occurred: can't send AntiLaby packages to " + p.getName() + " (" + p.getUniqueId() + ")!");
        }
    }
}
