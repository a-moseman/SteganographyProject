package org.amoseman.steganography.ui.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgParserTest {
    @Test
    void parse() {
        ArgParser parser = new ArgParser.Builder()
                .setPrefix("--")
                .addCommand("run")
                .addBoolean("bool")
                .addArray("arr", 2, true)
                .addList("list", true)
                .addVariable("var", true)
                .build();

        assertDoesNotThrow(() -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "c", "d", "e", "--var", "f"));
        assertDoesNotThrow(() -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "--var", "f"));

        assertThrows(IllegalArgumentException.class, () -> parser.parse());
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr", "--list", "--var"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr", "a", "b", "--list", "--var", "c", "d"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("blah"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--blah"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "blah"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "c", "d", "e", "--var", "f", "--var", "f"));
    }

    @Test
    void reduce() {
        assertArrayEquals(new String[]{"b"}, ArgParser.reduce("a", "b"));
        assertThrows(RuntimeException.class, () -> ArgParser.reduce());
    }
}