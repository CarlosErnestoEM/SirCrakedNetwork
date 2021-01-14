package com.pollogamer.sircrakedserver.sanciones.util;

public enum OffenseSeverity {

    SEVERITY1("Severidad 1", (short) 2),
    SEVERITY2("Severidad 2", (short) 11),
    SEVERITY3("Severidad 3", (short) 14),
    SEVERITY4("Severidad 4", (short) 1);

    private String displayName;
    private short stackData;

    OffenseSeverity(String displayName, short stackData) {
        this.displayName = displayName;
        this.stackData = stackData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public short getStackData() {
        return stackData;
    }
}
