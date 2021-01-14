package com.pollogamer.sircrakedserver.troll;

import com.pollogamer.sircrakedserver.troll.trolls.TrollRocket;
import com.pollogamer.sircrakedserver.troll.trolls.TrollTWD;
import com.pollogamer.sircrakedserver.troll.trolls.TrollTravelToMoon;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TrollManager {

    public static List<AbstractTroll> trolls = new ArrayList<>();
    public static List<Player> trolled = new ArrayList<>();

    public TrollManager() {
        registerTroll(new TrollRocket());
        registerTroll(new TrollTWD());
        registerTroll(new TrollTravelToMoon());
    }


    public void registerTroll(AbstractTroll troll) {
        trolls.add(troll);
    }
}
