package com.pollogamer.proxy.manager;

import com.pollogamer.proxy.fastlogin.FastLogin;
import com.pollogamer.proxy.object.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> modules = new ArrayList<>();
    private FastLogin fastLogin;

    public ModuleManager() {
        fastLogin = registerModule(new FastLogin());
    }

    public void onEnable() {
        for (Module module : modules) {
            module.onEnable();
        }
    }


    public <T extends Module> T registerModule(T module) {
        modules.add(module);
        return module;
    }

    public FastLogin getFastLogin() {
        return fastLogin;
    }

    public List<Module> getModules() {
        return modules;
    }
}
