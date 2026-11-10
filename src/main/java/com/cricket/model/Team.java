package com.cricket.model;

import java.util.Objects;

public final class Team {
    private final String name;
    public Team(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Team name cannot be blank.");
        this.name = name.trim();
    }
    public String getName() { return name; }
    @Override public boolean equals(Object o) { return o instanceof Team t && name.equalsIgnoreCase(t.name); }
    @Override public int hashCode() { return Objects.hash(name.toLowerCase()); }
    @Override public String toString() { return name; }
}
