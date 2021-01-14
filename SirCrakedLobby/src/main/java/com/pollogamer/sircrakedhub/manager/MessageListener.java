package com.pollogamer.sircrakedhub.manager;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.pollogamer.sircrakedhub.Principal;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {

    public int players = 0;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playerCount = in.readInt();
            players = playerCount;
        }
    }

    public void getCount(Player player, String server) {
        if (server == null) {
            server = "all";
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(Principal.plugin, "BungeeCord", out.toByteArray());
    }
}
