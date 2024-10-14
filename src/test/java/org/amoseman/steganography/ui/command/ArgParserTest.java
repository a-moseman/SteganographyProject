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

        // valid arguments
        assertDoesNotThrow(() -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "c", "d", "e", "--var", "f"));
        assertDoesNotThrow(() -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "--var", "f"));

        // invalid arguments

        // missing command
        assertThrows(IllegalArgumentException.class, () -> parser.parse());
        // missing arguments
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run"));
        // --arr is missing values
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr"));
        // --list is not a valid value for --a
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr", "--list", "--var"));
        // --var is missing value
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--var"));
        // d is orphan value
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--arr", "a", "b", "--list", "--var", "c", "d"));
        // blah is an invalid command
        assertThrows(IllegalArgumentException.class, () -> parser.parse("blah"));
        // --blah is an invalid argument
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--blah"));
        // blah is not an argument
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "blah"));
        // duplicate argument --var
        assertThrows(IllegalArgumentException.class, () -> parser.parse("run", "--bool", "--arr", "a", "b", "--list", "c", "d", "e", "--var", "f", "--var", "f"));
    }

    @Test
    void reduce() {
        assertArrayEquals(new String[]{"b"}, ArgParser.reduce("a", "b"));
        assertThrows(RuntimeException.class, () -> ArgParser.reduce());
    }
}