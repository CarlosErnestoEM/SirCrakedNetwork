package com.pollogamer.sircrakedserver.comandos;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.utils.Lang;
import com.pollogamer.sircrakedserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CMDFreeze implements CommandExecutor, Listener {

    public static List<String> freeze = new ArrayList<>();
    public static Map<Player, ItemStack> hat = new HashMap<>();

    public CMDFreeze() {
        init();
        Bukkit.getPluginManager().registerEvents(this, SirCrakedCore.getCore());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.prefix + "No tienes permiso para ejecutar este comando");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("sircraked.freeze")) return true;
        if (args.length == 0) {
            p.sendMessage(Lang.prefix + "Utiliza /freeze (Jugador)");
            return true;
        } else if (args.length == 1) {
            Player obj = Bukkit.getPlayer(args[0]);
            if (obj == null) {
                p.sendMessage(Lang.prefix + "El jugador " + args[0] + " no esta conectado!");
                return true;
            } else {
                if (obj.hasPermission("sircraked.freeze.bypass")) {
                    p.sendMessage(Lang.prefix + "No puedes congelar a " + obj.getName());
                    return true;
                }
                toggleFreeze(p, obj);
            }
        } else {
            p.sendMessage(Lang.prefix + "Utiliza /freeze (Jugador)");
        }
        return true;
    }

    public static boolean isFreeze(Player player) {
        return freeze.contains(player.getName());
    }

    public static void toggleFreeze(Player player, Player obj) {
        if (isFreeze(obj)) {
            unfreeze(player, obj);
        } else {
            freeze(player, obj);
        }
    }

    public static void freeze(Player p, Player obj) {
        freeze.add(obj.getName());
        p.sendMessage(Lang.prefix + "Has congelado a " + obj.getName());
        obj.sendMessage(Lang.prefix + "Has sido congelado por " + p.getName() + " colabore con la revision!");
        hat.put(obj, obj.getInventory().getHelmet());
        obj.getInventory().setHelmet(new ItemStackBuilder(Material.PACKED_ICE).setName("&aCasco de frezeados xdxd"));
    }

    public static void unfreeze(Player p, Player obj) {
        freeze.remove(obj.getName());
        p.sendMessage(Lang.prefix + "Has descongelado a " + obj.getName());
        obj.sendMessage(Lang.prefix + "Has sido descongelado por " + p.getName() + "!");
        obj.getInventory().setHelmet(hat.get(obj));
    }

    public static void unfreeze(Player obj) {
        freeze.remove(obj.getName());
        obj.getInventory().setHelmet(hat.get(obj));
    }

    @EventHandler
    public void onMove3(PlayerMoveEvent event) {
        if (Utils.moved(event)) {
            if (CMDFreeze.freeze.contains(event.getPlayer().getName())) {
                event.setTo(event.getFrom());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (CMDFreeze.isFreeze(player)) {
            CMDFreeze.unfreeze(player);
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.hasPermission("sircraked.ss")) {
                    player1.sendMessage(" ");
                    player1.sendMessage("§7" + player.getName() + " se desconecto congelado!");
                    player1.sendMessage(" ");
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (isFreeze(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    public void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String s : freeze) {
                    Player player = Bukkit.getPlayer(s);
                    if (player != null) {
                        player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, 174);
                    }
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 60, 60);
    }
}
