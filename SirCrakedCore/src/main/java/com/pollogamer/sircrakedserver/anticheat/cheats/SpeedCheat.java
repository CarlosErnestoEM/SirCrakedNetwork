package com.pollogamer.sircrakedserver.anticheat.cheats;

import com.pollogamer.sircrakedserver.anticheat.AbstractCheat;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedCheat extends AbstractCheat {

    public SpeedCheat() {
        super("Speed/Fly");
    }

    @EventHandler
    public void checkSpeed(PlayerMoveEvent event) {
        if (!isLag()) {
            if (!hasBypass(event.getPlayer())) {
                Player player = event.getPlayer();
                if (player.getGameMode().equals(GameMode.ADVENTURE) || player.getGameMode().equals(GameMode.SURVIVAL) || !player.isFlying() || !player.getAllowFlight()) {
                    Location from = event.getFrom();
                    Location to = event.getTo();
                    Block down = from.getBlock().getRelative(BlockFace.DOWN);
                    if (!isInvisible(down)) {
                        Block up = from.getBlock().getRelative(BlockFace.UP);
                        double distance = from.toVector().distance(to.toVector());
                        if (up.getType().equals(Material.AIR)) {
                            if (distance >= (isIce(down) ? 0.93 : 0.88)) {
                                notify(player, distance + " aire arriba");
                                event.setCancelled(true);
                                event.setTo(from);
                                player.teleport(from);
                            }
                        } else {
                            if (distance >= (isIce(down) ? 0.95 : 0.92)) {
                                notify(player, distance + " otro bloque arriba xdxd");
                                event.setCancelled(true);
                                event.setTo(from);
                                player.teleport(from);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isInvisible(Block block) {
        return block.getType().isTransparent();
    }

    private boolean isIce(Block block) {
        return block.getType().equals(Material.ICE) || block.getType().equals(Material.PACKED_ICE);
    }
}
