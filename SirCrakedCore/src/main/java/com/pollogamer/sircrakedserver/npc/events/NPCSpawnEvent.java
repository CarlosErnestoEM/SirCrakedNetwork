package com.pollogamer.sircrakedserver.npc.events;

import com.pollogamer.sircrakedserver.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class NPCSpawnEvent extends AbstractNPCEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public NPCSpawnEvent(Player player, NPC npc) {
        super(player, npc);
    }

}