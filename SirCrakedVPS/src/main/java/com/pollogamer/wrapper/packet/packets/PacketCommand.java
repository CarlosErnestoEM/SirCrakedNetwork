package com.pollogamer.wrapper.packet.packets;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.packet.PacketWrapper;
import com.pollogamer.wrapper.utils.StringUtils;

public class PacketCommand extends PacketWrapper {

    public PacketCommand() {
        super("command");
    }

    @Override
    public void execute(String[] args, McServer mcServer) {
        format("Packet-Manager", "Received packet " + getPacketName() + " from server " + mcServer.getServerName());
        if (args.length >= 3) {
            Main.getMain().dispatchCommand(("sendcommand " + args[1] + " " + getCommand(args)).split(" "));
        } else {
            format("ERROR", "Packed command are bad... Please usage command <Server> <Command>");
        }
    }


    public String getCommand(String[] args) {
        return StringUtils.join(args, 2, " ");
    }
}
