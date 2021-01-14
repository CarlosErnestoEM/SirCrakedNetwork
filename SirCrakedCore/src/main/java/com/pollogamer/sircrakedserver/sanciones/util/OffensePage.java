package com.pollogamer.sircrakedserver.sanciones.util;

public enum OffensePage {

    CHAT("Sanciones de chat", 10, 10),
    GAMEPLAY("Sanciones de Gameplay", 10, 10),
    PERMANENT("Sanciones Permanente", 19, -1),
    HISTORY("Historial", 0, 0),
    MAIN("Castigos", 0, 0);

    private String title;
    private int startSlot;
    private int startSlotHelper;

    OffensePage(String title, int startSlot, int startSlotHelper) {
        this.title = title;
        this.startSlot = startSlot;
        this.startSlotHelper = startSlotHelper;
    }

    public String getTitle() {
        return title;
    }

    public int getStartSlot() {
        return startSlot;
    }

    public int getStartSlotHelper() {
        return startSlotHelper;
    }
}
