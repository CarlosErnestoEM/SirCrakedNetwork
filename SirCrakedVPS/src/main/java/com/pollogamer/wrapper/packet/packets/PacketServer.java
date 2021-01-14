package com.pollogamer.wrapper.packet.packets;

import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.packet.PacketWrapper;

public class PacketServer extends PacketWrapper {

    public PacketServer() {
        super("server");
    }

    @Override
    public void execute(String[] args, McServer mcServer) {
        if (args.length == 3) {

        }
    }
}
