package com.pollogamer.sircrakedhub.comandos;

import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.inv.MenuPets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDPets implements CommandExecutor {

    private final Principal plugin;

    public CMDPets(Principal plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        new MenuPets(this.plugin, p);
        return true;
    }
}
