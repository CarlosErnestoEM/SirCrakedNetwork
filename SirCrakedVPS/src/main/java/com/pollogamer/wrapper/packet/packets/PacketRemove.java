package com.pollogamer.wrapper.packet.packets;

import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.packet.PacketWrapper;

public class PacketRemove extends PacketWrapper {

    public PacketRemove() {
        super("remove");
    }

    @Override
    public void execute(String[] args, McServer mcServer) {
        format("Packet-Manager", "Received Packet " + getPacketName() + " from server " + mcServer.getServerName());
        mcServer.stop(true);
    }
}
