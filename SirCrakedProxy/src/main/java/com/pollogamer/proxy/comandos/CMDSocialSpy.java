package com.pollogamer.proxy.comandos;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDSocialSpy extends Command {

    public CMDSocialSpy() {
        super("socialspy", "sircraked.socialspy", "sc");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§aSolo los jugadores pueden hacer eso");
            return;
        }
        if (CMDMSG.socialspy.contains(sender)) {
            CMDMSG.socialspy.remove(sender);
            sender.sendMessage("§4§lSir§1§lCraked §7» §aHas desactivado el modo espia");
        } else {
            CMDMSG.socialspy.add((ProxiedPlayer) sender);
            sender.sendMessage("§4§lSir§1§lCraked §7» §aHas activado el modo espia");
        }
    }
}
