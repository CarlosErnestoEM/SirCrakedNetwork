package com.pollogamer.sircrakedserver.sanciones.util;

import java.util.Arrays;

public enum Offense {

    // Ofensas de Chat
    SPAM_SEVERITY_1(OffensePage.CHAT, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Spam", "Hacer spam"), "No hagas spam"),
    SPAM_SEVERITY_2(OffensePage.CHAT, OffenseSeverity.SEVERITY2, false, PunishType.TEMPMUTE, true, "30m", new OffenseCause("Spam", "Hacer spam"), "Hacer spam"),
    SPAM_SEVERITY_3(OffensePage.CHAT, OffenseSeverity.SEVERITY3, false, PunishType.TEMPMUTE, true, "1d", new OffenseCause("Spam", "Hacer spam"), "Hacer spam"),
    SPAM_SEVERITY_4(OffensePage.CHAT, OffenseSeverity.SEVERITY4, false, PunishType.BAN, true, "Permanente", new OffenseCause("Publicidad", "Paso de IP de otro servidor"), "Pasar IP de otro servidor"),

    FLOOD_SEVERITY_1(OffensePage.CHAT, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Flood", "Llenar el chat innecesariamente"), "No hagas spam"),
    FLOOD_SEVERITY_2(OffensePage.CHAT, OffenseSeverity.SEVERITY2, false, PunishType.TEMPMUTE, true, "1h", new OffenseCause("Flood", "Llenar el chat innecesariamente"), "Llenar el chat innecesariamente"),
    FLOOD_SEVERITY_3(OffensePage.CHAT, OffenseSeverity.SEVERITY3, false, PunishType.TEMPMUTE, true, "3d", new OffenseCause("Flood", "Llenar el chat innecesariamente"), "Llenar el chat innecesariamente"),
    FLOOD_SEVERITY_4(OffensePage.CHAT, OffenseSeverity.SEVERITY4, false, PunishType.MUTE, true, "Permanente", new OffenseCause("Flood", "Llenar el chat innecesariamente"), "Llenar el chat innecesariamente"),

    ASK_SEVERITY_1(OffensePage.CHAT, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Pedir cosas", "Rango, kits, dinero, etc..."), "¡No puedes pedir eso al Staff!"),
    ASK_SEVERITY_2(OffensePage.CHAT, OffenseSeverity.SEVERITY2, false, PunishType.TEMPMUTE, true, "1h", new OffenseCause("Pedir cosas", "Rango, kits, dinero, etc..."), "Pedirle cosas al staff"),
    ASK_SEVERITY_3(OffensePage.CHAT, OffenseSeverity.SEVERITY3, false, PunishType.TEMPBAN, true, "1d", new OffenseCause("Pedir cosas", "Rango, kits, dinero, etc..."), "Pedirle cosas al staff"),
    ASK_SEVERITY_4(OffensePage.CHAT, OffenseSeverity.SEVERITY4, false, PunishType.TEMPMUTE, true, "3d", new OffenseCause("Pedir cosas", "Rango, kits, dinero, etc..."), "Pedirle cosas al staff"),

    DISRESPECT_SEVERITY_1(OffensePage.CHAT, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Insultos", "Falta de respeto hacia un jugador"), "Muestra un poco de respeto"),
    DISRESPECT_SEVERITY_2(OffensePage.CHAT, OffenseSeverity.SEVERITY2, false, PunishType.TEMPMUTE, true, "30m", new OffenseCause("Insultos", "Falta de respeto hacia un jugador"), "Falta de respeto hacia un jugador"),
    DISRESPECT_SEVERITY_3(OffensePage.CHAT, OffenseSeverity.SEVERITY3, false, PunishType.TEMPMUTE, true, "1d", new OffenseCause("Insultos", "Falta de respeto hacia un jugador"), "Falta de respeto hacia un jugador"),
    DISRESPECT_SEVERITY_4(OffensePage.CHAT, OffenseSeverity.SEVERITY4, false, PunishType.TEMPMUTE, true, "7d", new OffenseCause("Insultos", "Falta de respeto hacia un jugador"), "Falta de respeto hacia un jugador"),

    MISUSE_HELPOP_SEVERITY_1(OffensePage.CHAT, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Mal uso del HelpOp"), "El helpop no es para eso."),
    MISUSE_HELPOP_SEVERITY_2(OffensePage.CHAT, OffenseSeverity.SEVERITY2, false, PunishType.WARNING, true, "Permanente", new OffenseCause("Mal uso del HelpOp"), "El helpop no es para eso."),
    MISUSE_HELPOP_SEVERITY_3(OffensePage.CHAT, OffenseSeverity.SEVERITY3, false, PunishType.WARNING, true, "Permanente", new OffenseCause("Mal uso del HelpOp"), "A la próxima perderás el uso del HelpOp"),
    MISUSE_HELPOP_SEVERITY_4(OffensePage.CHAT, OffenseSeverity.SEVERITY4, false, PunishType.WARNING, true, "Permanente", new OffenseCause("Mal uso del HelpOp"), "Perdiste el uso de HelpOP :v"),

    //Ofensas de GamePlay
    CAMPING_SEVERITY_1(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Campear"), "No campees!"),
    CAMPING_SEVERITY_2(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY2, false, PunishType.TEMPBAN, true, "30m", new OffenseCause("Campear"), "Campear"),
    CAMPING_SEVERITY_3(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY3, false, PunishType.TEMPBAN, true, "1d", new OffenseCause("Campear"), "Campear"),
    CAMPING_SEVERITY_4(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY4, false, PunishType.BAN, true, "Permanente", new OffenseCause("Campear"), "Campear"),

    BUG_EXPLOIT_SEVERITY_1(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Abuso de bugs"), "Abuso de bugs"),
    BUG_EXPLOIT_SEVERITY_2(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY2, false, PunishType.TEMPBAN, true, "1h", new OffenseCause("Abuso de bugs"), "Abuso de bugs"),
    BUG_EXPLOIT_SEVERITY_3(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY3, false, PunishType.TEMPBAN, true, "1d", new OffenseCause("Abuso de bugs"), "Abuso de bugs"),
    BUG_EXPLOIT_SEVERITY_4(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY4, false, PunishType.BAN, true, "Permanente", new OffenseCause("Abuso de bugs"), "Abuso de bugs"),

    TEAM_1(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY1, true, PunishType.WARNING, true, "Permanente", new OffenseCause("Team"), "No hagas team!"),
    TEAM_2(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY2, false, PunishType.TEMPBAN, true, "30m", new OffenseCause("Team"), "No hagas team!"),
    TEAM_3(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY3, false, PunishType.TEMPBAN, true, "1d", new OffenseCause("Team"), "No hagas team!"),
    TEAM_4(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY4, false, PunishType.TEMPBAN, true, "Permanente", new OffenseCause("Team"), "No hagas team!"),

    HACKS_SEVERITY_1(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY1, true, PunishType.TEMPBAN, true, "2d", new OffenseCause("Uso de hacks", "KillAura, Speed, FastBreak, Regedit, etc"), "Uso de hacks"),
    HACKS_SEVERITY_4(OffensePage.GAMEPLAY, OffenseSeverity.SEVERITY4, false, PunishType.BAN, true, "Permanente", new OffenseCause("Uso de hacks", "KillAura, Speed, FastBreak, Regedit, etc"), "Uso de hacks"),

    //Ofensas permanentes
    BAN(OffensePage.PERMANENT, OffenseSeverity.SEVERITY4, true, PunishType.BAN, false, "Permanente", new OffenseCause("Ban", "Sanciona al jugador por una razón"), "custom"),
    MUTE(OffensePage.PERMANENT, OffenseSeverity.SEVERITY4, true, PunishType.MUTE, false, "Permanente", new OffenseCause("Mute", "Silencia al jugador por una razón"), "custom"),
    SHADOWMUTE(OffensePage.PERMANENT, OffenseSeverity.SEVERITY4, true, PunishType.SHADOWMUTE, false, "Permanente", new OffenseCause("Shadow Mute", "Silenciona al jugador por publicidad"), "custom");

    private OffensePage type;
    private OffenseSeverity severity;
    private PunishType puinishmentType;
    private String time;
    private OffenseCause offense;
    private String reason;
    private boolean helper;
    private boolean newColumn;

    Offense(OffensePage type, OffenseSeverity severity, boolean newColumn, PunishType punishType, boolean helper, String time, OffenseCause cause, String reason) {
        this.type = type;
        this.severity = severity;
        this.puinishmentType = punishType;
        this.time = time;
        this.offense = cause;
        this.reason = reason;
        this.newColumn = newColumn;
        this.helper = helper;
    }

    public String toString() {
        return "[pagina=" + type.getTitle() + ",Regla=" + offense.getReason() + ",ejemplo=" + Arrays.asList(offense.getExamples()).get(0) + ",severidad=" + severity.toString() + ",castigo=" + puinishmentType.toString() + ",duración=" + (time.equalsIgnoreCase("69d") ? "permanente" : time) + "]";
    }

    public OffensePage getType() {
        return type;
    }

    public OffenseSeverity getSeverity() {
        return severity;
    }

    public PunishType getPuinishmentType() {
        return puinishmentType;
    }

    public String getTime() {
        return time;
    }

    public OffenseCause getOffense() {
        return offense;
    }

    public String getReason() {
        return reason;
    }

    public boolean isHelper() {
        return helper;
    }

    public boolean isNewColumn() {
        return newColumn;
    }
}
