package org.amoseman.steganography;

import org.amoseman.steganography.ui.command.Parameters;
import org.amoseman.steganography.ui.command.UserInterface;

public class Main {
    public static void main(String[] args) {
        Parameters parameters = new UserInterface().parse(args);
    }
}
