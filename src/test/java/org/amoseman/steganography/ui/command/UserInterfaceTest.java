package org.amoseman.steganography.ui.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    @Test
    void run() {
        UserInterface ui = new UserInterface();

        // missing command
        assertThrows(IllegalArgumentException.class, () -> ui.parse());
        // missing --source and/or --target
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--target"));
        // --source and --target lack values
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "--target"));
        // --source needs one value, --target needs many
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "b", "--target", "a"));

        // missing command
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode"));
        // missing --source and/or --target
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--target"));
        // --source and --target lack values
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source", "--target"));
        // --source needs many, --target needs one
        assertThrows(IllegalArgumentException.class, () -> ui.parse("decode", "--source", "a", "--target", "a", "b"));

        // --password needs value
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "--target", "b", "--password"));
        // --compress is duplicated
        assertThrows(IllegalArgumentException.class, () -> ui.parse("encode", "--source", "a", "--target", "b", "--password", "pass", "--compress", "--compress"));
        // --source, --compress, and --password are not allowed for analyze
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--source", "a", "--target", "b"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--target", "b", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.parse("analyze", "--target", "b", "--password", "pass"));

        // valid arguments
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