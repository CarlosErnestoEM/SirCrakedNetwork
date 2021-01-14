package com.pollogamer.sircrakedserver.hologram;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class HoloListener implements Listener {

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e) {
        if (!e.getRightClicked().isVisible()) {
            e.setCancelled(true);
        }
    }


}
