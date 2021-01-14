package com.pollogamer.sircrakedserver.packet.packets;

import com.pollogamer.sircrakedserver.packet.PacketWrapper;
import com.pollogamer.sircrakedserver.utils.StringUtils;
import org.bukkit.Bukkit;

public class PacketCommand extends PacketWrapper {

    public PacketCommand() {
        super("command");
    }

    @Override
    public void execute(String[] args) {
        format("Packet-Command", "Received packet command");
        if (args.length >= 2) {
            String command = StringUtils.join(args, 1, " ");
            format("COMMAND-DISPATCHER", "Dispatching command " + command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } else {
            format("ERROR", "The packet are bad xdxd");
        }
    }
}
