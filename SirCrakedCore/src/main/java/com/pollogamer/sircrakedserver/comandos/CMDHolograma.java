package com.pollogamer.sircrakedserver.comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CMDHolograma implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("holograma.admin")) return true;

        if (args.length == 0) {
            p.sendMessage("Utiliza holograma create (Nombre)");
        } else if (args.length == 1) {
            p.sendMessage("Utiliza /holograma create (Nombre)");
        } else if (args.length == 2) {
            ArmorStand as = (ArmorStand) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND); //Spawn the ArmorStand

            as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
            as.setVisible(false); //Makes the ArmorStand invisible
            as.setGravity(false); //Make sure it doesn't fall
            as.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
            as.setCustomName(ChatColor.translateAlternateColorCodes('&', args[1])); //Set this to the text you want
            p.sendMessage("Creaste el holograma " + args[1]);
        } else {
            p.sendMessage("Utiliza /holograma create (Nombre)");
        }
        return true;
    }
}
