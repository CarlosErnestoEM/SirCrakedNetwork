package com.pollogamer.sircrakedffa.comandos;

import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedffa.manager.Lang;
import com.pollogamer.sircrakedserver.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDFFA implements CommandExecutor {

    private Principal plugin;

    public CMDFFA(Principal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.ffacommand")) {
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("Utiliza /sircrakedffa setffaspawn,setcombospawn,setbuildspawn");
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "setffaspawn":
                    plugin.getConfig().set("spawns.ffa", Utils.serializeLoc(p.getLocation()));
                    Lang.init();
                    plugin.saveConfig();
                    break;

                case "setcombospawn":
                    plugin.getConfig().set("spawns.comboffa", Utils.serializeLoc(p.getLocation()));
                    Lang.init();
                    plugin.saveConfig();
                    break;

                case "setbuildspawn":
                    plugin.getConfig().set("spawns.buildffa", Utils.serializeLoc(p.getLocation()));
                    Lang.init();
                    plugin.saveConfig();
                    break;

                default:
                    p.sendMessage("Utiliza /sircrakedffa setffaspawn,setcombospawn,setbuildspawn");
                    Lang.init();
                    plugin.saveConfig();
                    break;
            }
        } else {
            p.sendMessage("Utiliza /sircrakedffa setffaspawn,setcombospawn,setbuildspawn");
        }
        return true;
    }
}
