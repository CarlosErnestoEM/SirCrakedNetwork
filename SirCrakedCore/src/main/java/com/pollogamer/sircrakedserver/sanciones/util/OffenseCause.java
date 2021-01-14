package com.pollogamer.sircrakedserver.sanciones.util;

public class OffenseCause {

    private final String reason;
    private final String[] examples;

    public OffenseCause(String reason, String... examples) {
        this.reason = reason;
        this.examples = examples;
    }

    public String getReason() {
        return reason;
    }

    public String[] getExamples() {
        return examples;
    }
}
