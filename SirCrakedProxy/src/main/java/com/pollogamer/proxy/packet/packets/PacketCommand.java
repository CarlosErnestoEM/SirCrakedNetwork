package com.pollogamer.proxy.packet.packets;

import com.pollogamer.proxy.packet.PacketWrapper;
import com.pollogamer.proxy.utils.StringUtils;
import net.md_5.bungee.BungeeCord;

public class PacketCommand extends PacketWrapper {

    public PacketCommand() {
        super("command");
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 2) {
            format("Packet-Manager", "Received packet " + getPacketName());
            String command = StringUtils.join(args, 1, " ");
            format("COMMAND-DISPATCHER", "Dispatching command " + command);
            BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), command);
        } else {
            format("ERROR", "Received bad packet " + getPacketName());
        }
    }
}