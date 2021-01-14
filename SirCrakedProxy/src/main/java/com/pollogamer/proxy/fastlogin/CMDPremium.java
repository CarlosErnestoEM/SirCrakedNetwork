package com.pollogamer.proxy.fastlogin;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMDPremium extends Command {

    private String usage = "Utiliza /premium <Username>";

    public CMDPremium() {
        super("premium");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            if (args.length == 1) {
                boolean status = togglePremium(args[0]);
                sender.sendMessage("§4§lSir§1§lCraked §7» §aHas " + (status ? "desactivado" : "activado") + " el modo premium para " + args[0]);
                ProxiedPlayer proxiedPlayer = BungeeCord.getInstance().getPlayer(args[0]);
                if (proxiedPlayer != null) {
                    proxiedPlayer.sendMessage("§4§lSir§1§lCraked §7» §aHas " + (status ? "desactivado" : "activado") + " el modo premium!");
                }
            } else {
                sender.sendMessage(usage);
            }
            return;
        }
        String playerName = sender.getName();
        if (args.length == 0) {
            boolean status = togglePremium(playerName);
            sender.sendMessage("§4§lSir§1§lCraked §7» §aHas " + (status ? "desactivado" : "activado") + " el modo premium!");
        }
    }

    public boolean togglePremium(String playerName) {
        boolean status = FastLogin.isPremiumDB(playerName);
        uploadToMySQL(playerName, !status);
        return status;
    }


    public void uploadToMySQL(String playerName, boolean premium) {
        Principal.getPlugin().getModuleManager().getFastLogin().getHikariSQLManager().Update("INSERT INTO premium(username, enabled) VALUES('" + playerName.toLowerCase() + "', '" + (premium ? 1 : 0) + "') ON DUPLICATE KEY UPDATE enabled=" + (premium ? 1 : 0));
        Principal.getPlugin().getModuleManager().getFastLogin().getPremiumPlayers().put(playerName, premium);
    }
}