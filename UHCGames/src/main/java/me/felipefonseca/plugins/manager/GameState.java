/*
 * Decompiled with CFR 0_118.
 */
package me.felipefonseca.plugins.manager;

public enum GameState {
    WAITING,
    STARTING,
    IN_GAME,
    DEATHMATCH,
    ENDING;

    public static GameState state;

    private GameState() {
    }

    public static enum State {
        PPREGAME,
        NO_PVP,
        PVP;

        public static State state;

        private State() {
        }
    }

}

