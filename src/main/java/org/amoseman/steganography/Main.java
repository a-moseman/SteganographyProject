package org.amoseman.steganography;

import org.amoseman.steganography.processing.parameters.ProcessingParameters;
import org.amoseman.steganography.ui.command.UserInterface;

public class Main {
    public static void main(String[] args) {
        ProcessingParameters parameters = new UserInterface().run(args);
    }
}
