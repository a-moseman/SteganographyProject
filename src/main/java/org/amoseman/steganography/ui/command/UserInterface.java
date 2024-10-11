package org.amoseman.steganography.ui.command;

/**
 * Provides command line argument parsing functionality.
 */
public class UserInterface {
    private final ArgParser parser;

    public UserInterface() {
        this.parser = new ArgParser.Builder()
                .setPrefix("--")
                .addCommand("encode")
                .addCommand("decode")
                .addCommand("analyze")
                .addBoolean("compress")
                .addVariable("password", false)
                .addList("source", false)
                .addList("target", true)
                .build();
    }

    public Parameters run(String... args) {
        Parameters parameters = parser.parse(args);
        assertValid(parameters);
        return parameters;
    }

    private void assertValid(Parameters parameters) {
        switch (parameters.command()) {
            case "analyze" -> {
                if (parameters.has("source")) {
                    throw new IllegalArgumentException("The --source argument is not used for analyze.");
                }
                if (parameters.has("compress")) {
                    throw new IllegalArgumentException("The --compress argument is not used for analyze.");
                }
                if (parameters.has("password")) {
                    throw new IllegalArgumentException("The --password argument is not used for analyze.");
                }
            }
            case "encode" -> {
                if (!parameters.has("source")) {
                    throw new IllegalArgumentException("The --source argument is required when encoding.");
                }
                if (parameters.get("source").length != 1) {
                    throw new IllegalArgumentException("One file path is required for the --source argument when encoding.");
                }
                if (parameters.get("target").length == 0) {
                    throw new IllegalArgumentException("At least one file path is required for the --target argument when encoding.");
                }
                if (!parameters.has("source")) {
                    throw new IllegalArgumentException("The --source argument is required for encoding and decoding.");
                }
            }
            case "decode" -> {
                if (!parameters.has("source")) {
                    throw new IllegalArgumentException("The --source argument is required when decoding.");
                }
                if (parameters.get("source").length == 0) {
                    throw new IllegalArgumentException("At least one file path is required for the --source argument when decoding.");
                }
                if (parameters.get("target").length != 1) {
                    throw new IllegalArgumentException("One file path is required for the --target argument when decoding.");
                }
                if (!parameters.has("source")) {
                    throw new IllegalArgumentException("The --source argument is required for encoding and decoding.");
                }
            }
        }
    }
}
