package com.pollogamer.proxy.comandos;

import com.pollogamer.proxy.Principal;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMDMantenimiento extends Command {

    public CMDMantenimiento() {
        super("mantenimiento");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sircraked.mantenimiento")) {
            sender.sendMessage(Principal.getPlugin().getPrefix() + "No puedes hacer eso!");
            return;
        }

        Principal.getPlugin().mantenimiento = !Principal.getPlugin().mantenimiento;
        sender.sendMessage("Has " + (Principal.getPlugin().mantenimiento ? "activado" : "desactivado") + " el modo mantenimiento!");
    }

}

