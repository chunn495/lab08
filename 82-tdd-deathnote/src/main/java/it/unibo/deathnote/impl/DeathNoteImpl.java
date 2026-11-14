package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private static final long MAX_CAUSE_TIME = 40;
    private static final long MAX_DETAILS_TIME = 6040;
    private static final String DEFAULT_CAUSE = "heart attack";
    private static final String DEFAULT_DETAILS = "";

    private final Map<String, Pair<String, String>> deathNote = new HashMap<>();
    private String currentName;
    private long time;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Rule numbe " + ruleNumber + " doeas not exist");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        if (name.isEmpty()){
            return;
        }
        this.currentName = name;
        this.deathNote.put(name, new Pair<String, String>("heart attack", ""));
        this.time = System.currentTimeMillis();
    }

    @Override 
    public boolean writeDeathCause(String cause){
        if(cause == null || this.deathNote.isEmpty()){
            throw new IllegalStateException();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.time <= 40) {
            this.deathNote.get(this.currentName).setFirst(cause);
            this.time = currentTimeMillis;
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null || this.deathNote.isEmpty()) {
            throw new IllegalStateException();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.time <= 6040) {
            this.deathNote.get(this.currentName).setSecond(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if (isNameWritten(name)) {
            return this.deathNote.get(name).getFirst();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getDeathDetails(String name) {

        if (isNameWritten(name)) {
            return this.deathNote.get(name).getSecond();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.deathNote.containsKey(name);
    }

    public class Pair<T, R> {
        private T first;
        private R second;

        public Pair(T first, R second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public R getSecond() {
            return second;
        }

        public void setSecond(R second) {
            this.second = second;
        }
    }
}


