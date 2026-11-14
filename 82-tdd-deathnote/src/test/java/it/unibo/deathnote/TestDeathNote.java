package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.deathnote.api.DeathNote.RULES;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDeathNote {
    private static final int NEGATIVE_NUMBER = -1;
    private static final int SLEEP_CAUSE = 100;
    private static final int SLEEP_DETAILS = 6100;
    private static final String NAME1 = "Alice";
    private static final String NAME2 = "Bob";
    private static final String CAUSE = "cause";
    private static final String DETAILS = "details";
    private static final String DEFAULT_CAUSE = "heart attack";
    private static final String DEFAULT_DETAILS = "";
    private DeathNote deathNote;

    @BeforeEach
    public void setUp() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    public void testGetRule() {
        for (int i = 1; i <= RULES.size(); i++) {
            final String rule = deathNote.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
        final var exZero = assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(0));
        assertNotNull(exZero.getMessage());
        assertFalse(exZero.getMessage().isBlank());

        final var exNeg = assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(NEGATIVE_NUMBER));
        assertNotNull(exNeg.getMessage());
        assertFalse(exNeg.getMessage().isBlank());

        final var exOver = assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(RULES.size() + 1));
        assertNotNull(exOver.getMessage());
        assertFalse(exOver.getMessage().isBlank());
    }

    @Test
    public void testWriteName() {
        assertThrows(NullPointerException.class, () -> this.deathNote.writeName(null));
        deathNote.writeName(NAME1);
        assertTrue(deathNote.isNameWritten(NAME1));
    }

    @Test
    public void testWriteDeathCause() throws InterruptedException {
        assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(null));
        assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(CAUSE));
        deathNote.writeName(NAME1);
        assertTrue(deathNote.writeDeathCause(CAUSE));
        deathNote.writeName(NAME2);
        sleep(SLEEP_CAUSE);
        assertFalse(deathNote.writeDeathCause(CAUSE));
    }

    @Test
    public void testWriteDetails() throws InterruptedException {
        assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(null));
        assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(CAUSE));
        deathNote.writeName(NAME1);
        deathNote.writeDeathCause(CAUSE);
        assertTrue(deathNote.writeDetails(DETAILS));
        deathNote.writeName(NAME2);
        deathNote.writeDeathCause(CAUSE);
        sleep(SLEEP_DETAILS);
        assertFalse(deathNote.writeDetails(DETAILS));
    }

    @Test
    public void testGetDeathCause() throws InterruptedException {
        assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathCause(NAME1));
        deathNote.writeName(NAME1);
        assertEquals(DEFAULT_CAUSE, deathNote.getDeathCause(NAME1));
        deathNote.writeDeathCause(CAUSE);
        assertEquals(CAUSE, deathNote.getDeathCause(NAME1));

        deathNote.writeName(NAME2);
        sleep(SLEEP_CAUSE);
        deathNote.writeDeathCause(CAUSE);
        assertEquals(DEFAULT_CAUSE, deathNote.getDeathCause(NAME2));
    }

    @Test
    public void testGetDeathDetails() throws InterruptedException {
        assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathDetails("Daniele"));
        deathNote.writeName(NAME1);
        assertEquals(DEFAULT_DETAILS, deathNote.getDeathDetails(NAME1));
        deathNote.writeDeathCause(CAUSE);
        deathNote.writeDetails(DETAILS);
        assertEquals(DETAILS, deathNote.getDeathDetails(NAME1));

        deathNote.writeName(NAME2);
        deathNote.writeDeathCause(CAUSE);
        sleep(SLEEP_DETAILS);
        deathNote.writeDetails(DETAILS);
        assertEquals(DEFAULT_DETAILS, deathNote.getDeathDetails(NAME2));
    }

    @Test
    public void testIsNameWritten() {
        assertFalse(deathNote.isNameWritten(NAME1));
        deathNote.writeName(NAME1);
        assertTrue(deathNote.isNameWritten(NAME1));
        assertFalse(deathNote.isNameWritten(NAME2));
        assertFalse(deathNote.isNameWritten(""));
    }

}