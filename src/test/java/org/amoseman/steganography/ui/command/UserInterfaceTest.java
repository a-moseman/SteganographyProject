package org.amoseman.steganography.ui.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    @Test
    void run() {
        UserInterface ui = new UserInterface();

        assertThrows(IllegalArgumentException.class, () -> ui.parse());
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "b", "--target", "a"));

        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source", "a", "--target", "a", "b"));

        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "--target", "b", "--password"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "--target", "b", "--password", "pass", "--compress", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--source", "a", "--target", "b"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--target", "b", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--target", "b", "--password", "pass"));

        assertDoesNotThrow(() -> ui.parse("encode", "--source", "a", "--target", "b", "c"));
        assertDoesNotThrow(() -> ui.parse("decode", "--source", "a", "b", "--target", "c"));
        assertDoesNotThrow(() -> ui.parse("encode", "--source", "a", "--target", "b", "c", "--password", "pass"));
        assertDoesNotThrow(() -> ui.parse("decode", "--source", "a", "b", "--target", "c", "--password", "pass"));
        assertDoesNotThrow(() -> ui.parse("encode", "--source", "a", "--target", "b", "c", "--password", "pass", "--compress"));
        assertDoesNotThrow(() -> ui.parse("decode", "--source", "a", "b", "--target", "c", "--password", "pass", "--compress"));
        assertDoesNotThrow(() -> ui.parse("analyze", "--target", "a"));
        assertDoesNotThrow(() -> ui.parse("analyze", "--target", "a", "b"));
    }
}