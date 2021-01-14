package com.pollogamer.sircrakedserver.npc.events;

import com.pollogamer.sircrakedserver.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbstractNPCEvent extends Event {

    private Player player;
    private NPC npc;

    public AbstractNPCEvent(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }


    public Player getPlayer() {
        return this.player;
    }

    public NPC getNPC() {
        return npc;
    }
}