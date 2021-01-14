package com.pollogamer.sircrakedserver.anticheat;

import com.pollogamer.sircrakedserver.anticheat.cheats.MacrosCheat;
import com.pollogamer.sircrakedserver.anticheat.cheats.SpeedCheat;
import com.pollogamer.sircrakedserver.objects.Module;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CheatModule extends Module {

    private List<AbstractCheat> cheats = new ArrayList<>();
    public static final CommandSender commandSender = new SCASender();

    @Override
    public void onEnable() {
        format("AntiCheat", "Starting the anticheat files...");
        registerCheats();
        format("AntiCheat", "Anticheat loaded successfully");
    }

    public void registerCheats() {
        registerCheat(new SpeedCheat());
        registerCheat(new MacrosCheat());
    }

    public void registerCheat(AbstractCheat abstractCheat) {
        cheats.add(abstractCheat);
        format("AntiCheat", "Registering " + abstractCheat.getCheatName() + " cheat!");
    }
}
