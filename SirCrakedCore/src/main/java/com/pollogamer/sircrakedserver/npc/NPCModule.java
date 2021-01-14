package com.pollogamer.sircrakedserver.npc;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.npc.tinyprotocol.PacketListener;
import com.pollogamer.sircrakedserver.objects.Module;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class NPCModule extends Module {

    private List<NPC> npcs = new ArrayList<>();

    @Override
    public void onEnable() {
        format("NPC", "Enabling NPC module...");
        startTask();
        format("NPC", "Registering listener...");
        PacketListener.startListening(this);
        format("NPC", "Has loaded!");
    }

    public NPC createNPC(Location location, String skin) {
        return addNPC(new NPC(location, skin));
    }

    public void removeNPC(NPC npc) {
        npcs.remove(npc.delete());
    }

    public NPC addNPC(NPC npc) {
        npcs.add(npc);
        return npc;
    }


    public void startTask() {
        format("NPC", "Task NPC Started!");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (NPC npc : npcs) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (npc.getLocation().getWorld().equals(pl.getWorld())) {
                            if (npc.getLocation().distance(pl.getLocation()) > 60 && npc.getRendered().contains(pl)) {
                                npc.destroy(pl);
                            } else if (npc.getLocation().distance(pl.getLocation()) < 60 && !npc.getRendered().contains(pl)) {
                                npc.spawn(pl);
                            }
                        } else {
                            npc.destroy(pl);
                        }
                    }
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 0, 30);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }
}
