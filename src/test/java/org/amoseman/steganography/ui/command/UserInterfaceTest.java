package org.amoseman.steganography.ui.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    UserInterface ui;

    @BeforeEach
    void setup() {
        ui = new UserInterface();
    }

    @Test
    void run() {
        assertThrows(IllegalArgumentException.class, () -> ui.run());

        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target", "example/path", "--source", "example/path"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target", "path", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("analyze", "--target", "path", "--password", "pass"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("encode"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "to", "many", "--target", "one"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("decode", "--source", "one", "--target", "to", "many"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "one", "--target", "to", "many", "--compress", "--compress"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "one", "--target", "to", "many", "--target", "to", "many"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "one", "--target", "to", "many", "--source", "one"));
        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "one", "--target", "to", "many", "--password", "pass", "--password", "pass"));

        assertThrows(IllegalArgumentException.class, () -> ui.run("encode", "--source", "path", "--target", "path", "--password"));

        assertDoesNotThrow(() -> ui.run("analyze", "--target", "path"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "path", "--target", "path"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "path", "--target", "path", "path"));
        assertDoesNotThrow(() -> ui.run("decode", "--source", "path", "path", "--target", "path"));
        assertDoesNotThrow(() -> ui.run("decode", "--source", "path", "--target", "path"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "path", "--target", "path", "--compress"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "path", "--target", "path", "--password", "pass"));
        assertDoesNotThrow(() -> ui.run("encode", "--source", "path", "--target", "path", "--password", "pass", "--compress"));
    }

    @Test
    void reduce() {
        String[] arr = {"a", "b", "c", "d"};
        arr = ui.reduce(1, arr);
        assertArrayEquals(new String[]{"b", "c", "d"}, arr);

        arr = ui.reduce(2, arr);
        assertArrayEquals(new String[]{"d"}, arr);

        arr = ui.reduce(1, arr);
        assertArrayEquals(new String[0], arr);

        String[] finalArr = arr;
        assertThrows(IllegalArgumentException.class, () -> ui.reduce(1, finalArr));
        assertThrows(IllegalArgumentException.class, () -> ui.reduce(-1, finalArr));
    }
}