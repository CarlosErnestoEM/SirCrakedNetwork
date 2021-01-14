package com.pollogamer.uhcsimulator.task;

import com.pollogamer.sircrakedserver.utils.MessagesController;
import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import com.pollogamer.uhcsimulator.vote.scenarios.drop.DropsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {

    public static int count = 30;

    public LobbyTask() {
        count = 30;
        runTaskTimer(Principal.getPlugin(), 0, 20);
    }

    @Override
    public void run() {
        if (!checkPlayers()) {
            if (count == 30 || count == 25 || count == 20 || count == 15 || count == 10) {
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl juego iniciara en " + count + " segundos!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setLevel(count);
                    MessagesController.sendTitle(player, "&a&l" + count, "", 10, 20, 10);
                }
            } else if (count > 0 && count <= 5) {
                Bukkit.broadcastMessage("§6§lUHCSimulator §7» §aEl juego iniciara en " + count + (count == 1 ? " segundo!" : " segundos!"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    MessagesController.sendTitle(player, "&a&l" + count, "", 10, 20, 10);
                }
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setLevel(count);
            }
            if (count >= 0) {
                if (count == 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        MessagesController.sendTitle(player, "§cTeletransportando...", "§aEspera 1 momento xd", 10, 20, 10);
                    }
                    DropsManager.setWinnerScenario();
                    new PreGameTask();
                    Lang.started = true;
                    cancel();
                } else {
                    --count;
                }
            }
        }
    }


    public boolean checkPlayers() {
        if (Bukkit.getOnlinePlayers().size() < Lang.minplayers) {
            if (!Lang.forcestart) {
                stopTask();
                return true;
            }
        }
        return false;
    }

    public void stopTask() {
        Bukkit.broadcastMessage("§6§LUHCSimulator §7» §aInicio detenido ya que no son los minimos jugadores! :c");
        Lang.starting = false;
        cancel();
    }
}
