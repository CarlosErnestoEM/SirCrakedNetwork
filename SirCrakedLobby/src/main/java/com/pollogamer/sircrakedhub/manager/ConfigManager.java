package com.pollogamer.sircrakedhub.manager;

import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedserver.objects.Config;

public class ConfigManager {

    public static Config servers;

    public ConfigManager() {
        servers = new Config(Principal.plugin, "servers.yml");
    }
}
