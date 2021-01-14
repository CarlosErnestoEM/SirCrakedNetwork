package me.felipefonseca.plugins.utils;

import java.util.List;

public class MessagesLoader {
    private static List welcomeMessage;


    public static String getWaitingState() {
        return "§aEsperando...";
    }

    public static String getStartingState() {
        return "§cEmpezando...";
    }

    public static String getIngameState() {
        return "§cEn juego";
    }

    public static String getDeathMatchState() {
        return "§4DeathMatch";
    }

    public static String getEndingState() {
        return "§cTerminando";
    }

    public static String getGotToLobby() {
        return "§7Seras mandado a la Lobby.";
    }

    public static String getGoldenHead() {
        return "§aGolden Apple";
    }

    public static String getItemGoToLobby() {
        return "§c§lRegresa a la Lobby";
    }

    public static List getWelcomeMessage() {
        return welcomeMessage;
    }
}

