package com.pokeapp.app.Enum;

import java.util.Arrays;

public enum SortOptions {
    NAME,
    SIZE;

    public static SortOptions fromString(String value) {
        for (SortOptions option : SortOptions.values()) {
            if (option.name().equalsIgnoreCase(value)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
