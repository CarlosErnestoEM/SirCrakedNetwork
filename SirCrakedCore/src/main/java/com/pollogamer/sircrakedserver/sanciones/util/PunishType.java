package com.pollogamer.sircrakedserver.sanciones.util;

public enum PunishType {

    TEMPMUTE("Silencio"),
    MUTE("Silencio"),
    SHADOWMUTE("Silencio §c§l(NO USAR)"),
    TEMPBAN("Ban"),
    BAN("Ban"),
    WARNING("Advertencia");

    String displayName;

    PunishType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
