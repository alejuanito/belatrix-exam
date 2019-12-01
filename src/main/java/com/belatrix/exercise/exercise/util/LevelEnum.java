package com.belatrix.exercise.exercise.util;

import java.util.logging.Level;

public enum LevelEnum {
    ERROR("ERROR"),
    WARNING("WARNING"),
    INFO("INFO");

    private LevelEnum(String codigo) {
        this.codigo = codigo;
    }

    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    public static boolean contains(String test) {
        for (LevelEnum c : LevelEnum.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public static Level getLevel(String level) throws Exception{
        switch(level) {
            case "ERROR": return Level.SEVERE;
            case "WARNING": return Level.WARNING;
            case "MESSAGE": return Level.INFO;
            default : throw new Exception("Error or Warning or Message must be specified");
        }
    }
}
