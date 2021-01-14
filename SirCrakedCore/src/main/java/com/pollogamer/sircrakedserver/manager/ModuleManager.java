package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.sircrakedserver.anticheat.CheatModule;
import com.pollogamer.sircrakedserver.npc.NPCModule;
import com.pollogamer.sircrakedserver.objects.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> modules = new ArrayList<>();
    private CheatModule cheatModule;
    private NPCModule npcModule;

    public ModuleManager() {
        registerModule(cheatModule = new CheatModule());
        registerModule(npcModule = new NPCModule());
    }

    public void registerModule(Module module) {
        modules.add(module);
        module.onEnable();
    }

    public CheatModule getCheatModule() {
        return cheatModule;
    }

    public NPCModule getNpcModule() {
        return npcModule;
    }

    public List<Module> getModules() {
        return modules;
    }
}
