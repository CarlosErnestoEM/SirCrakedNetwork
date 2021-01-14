package com.pollogamer.sircrakedserver.npc.tinyprotocol;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.npc.NPC;
import com.pollogamer.sircrakedserver.npc.NPCModule;
import com.pollogamer.sircrakedserver.npc.events.NPCInteractEvent;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PacketListener {

    private static TinyProtocol protocol = null;

    private static Class<?> EntityInteractClass = Reflection.getClass("{nms}.PacketPlayInUseEntity");
    private static Reflection.FieldAccessor<Integer> EntityID = Reflection.getField(EntityInteractClass, int.class, 0);
    private static ArrayList<Player> playerswhointeract = new ArrayList<Player>();


    public static void startListening(NPCModule npcModule) {
        if (protocol == null) {
            protocol = new TinyProtocol(SirCrakedCore.getCore()) {
                @Override
                public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
                    if (EntityInteractClass.isInstance(packet)) {
                        if (!playerswhointeract.contains(sender)) {
                            for (NPC npc : npcModule.getNpcs()) {
                                if (npc.getEntityID() == EntityID.get(packet)) {
                                    NPCInteractEvent event = new NPCInteractEvent(sender, npc);
                                    Bukkit.getPluginManager().callEvent(event);
                                    break;
                                }
                            }
                            playerswhointeract.add(sender);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    playerswhointeract.remove(sender);
                                }
                            }.runTaskLaterAsynchronously(SirCrakedCore.getCore(), 2);
                        }
                    }
                    return super.onPacketInAsync(sender, channel, packet);
                }
            };
        }
    }

}