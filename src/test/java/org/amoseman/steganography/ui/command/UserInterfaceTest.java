package org.amoseman.steganography.ui.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    @Test
    void run() {
        UserInterface ui = new UserInterface();

        assertThrows(IllegalArgumentException.class, () -> ui.run());
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "a", "b", "--target", "a"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("decode"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("decode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("decode", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("decode", "--source", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("decode", "--source", "a", "--target", "a", "b"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "a", "--target", "b", "--password"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "a", "--target", "b", "--password", "pass", "--compress", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--source", "a", "--target", "b"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target", "b", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target", "b", "--password", "pass"));

        assertDoesNotThrow(() -> ui.run("encode", "--source", "a", "--target", "b", "c"));
        assertDoesNotThrow(() -> ui.run("decode", "--source", "a", "b", "--target", "c"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "a", "--target", "b", "c", "--password", "pass"));
        assertDoesNotThrow(() -> ui.run("decode", "--source", "a", "b", "--target", "c", "--password", "pass"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "a", "--target", "b", "c", "--password", "pass", "--compress"));
        assertDoesNotThrow(() -> ui.run("decode", "--source", "a", "b", "--target", "c", "--password", "pass", "--compress"));
        assertDoesNotThrow(() -> ui.run("analyze", "--target", "a"));
        assertDoesNotThrow(() -> ui.run("analyze", "--target", "a", "b"));
    }
}