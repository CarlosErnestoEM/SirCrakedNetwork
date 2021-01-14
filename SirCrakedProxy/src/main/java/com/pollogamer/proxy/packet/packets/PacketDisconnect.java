package com.pollogamer.proxy.packet.packets;

import com.pollogamer.proxy.Principal;
import com.pollogamer.proxy.packet.PacketWrapper;
import net.md_5.bungee.BungeeCord;

import java.util.concurrent.TimeUnit;

public class PacketDisconnect extends PacketWrapper {

    public static boolean canConnect;

    public PacketDisconnect() {
        super("disconnect");
        canConnect = true;
    }

    @Override
    public void execute(String[] args) {
        format("Packet-Manager", "Received packet " + getPacketName());
        try {
            format("ServerConnector", "Disconnected from SirCraked Wrapper...");
            Principal.getPlugin().getWrapperConnector().getSocket().close();
            Principal.getPlugin().getWrapperConnector().getDataOutputStream().close();
            Principal.getPlugin().getWrapperConnector().getDataInputStream().close();
            canConnect = false;
            BungeeCord.getInstance().getScheduler().schedule(Principal.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    canConnect = true;
                }
            }, 1, TimeUnit.SECONDS);
            Principal.getPlugin().getWrapperConnector().getThread().stop();
            log("thread closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
