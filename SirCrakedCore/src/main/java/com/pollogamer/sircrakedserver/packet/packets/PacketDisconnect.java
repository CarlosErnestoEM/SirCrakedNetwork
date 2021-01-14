package com.pollogamer.sircrakedserver.packet.packets;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.packet.PacketWrapper;
import org.bukkit.scheduler.BukkitRunnable;

public class PacketDisconnect extends PacketWrapper {

    public static boolean canConnect;

    public PacketDisconnect() {
        super("disconnect");
        canConnect = true;
    }

    @Override
    public void execute(String[] args) {
        format("Packet-Disconnect", "Received packet disconnect");
        try {
            format("ServerConnector", "Disconnected from SirCraked Wrapper...");
            SirCrakedCore.getCore().getWrapperConnector().getSocket().close();
            SirCrakedCore.getCore().getWrapperConnector().getDataOutputStream().close();
            SirCrakedCore.getCore().getWrapperConnector().geg().close();
            canConnect = false;
            new BukkitRunnable() {
                @Override
                public void run() {
                    canConnect = true;
                }
            }.runTaskLater(SirCrakedCore.getCore(), 10);
            SirCrakedCore.getCore().getWrapperConnector().getThread().stop();
            log("thread closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
