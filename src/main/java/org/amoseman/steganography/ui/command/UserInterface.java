package org.amoseman.steganography.ui.command;

/**
 * Provides a user interface for the program.
 */
public class UserInterface {
    public static final String ARGUMENT_PREFIX = "--";
    public static final String ENCODE_COMMAND = "encode";
    public static final String DECODE_COMMAND = "decode";
    public static final String ANALYZE_COMMAND = "analyze";
    public static final String SOURCE_ARGUMENT = "source";
    public static final String TARGET_ARGUMENT = "target";
    public static final String COMPRESS_ARGUMENT = "compress";
    public static final String PASSWORD_ARGUMENT = "password";

    private final ArgParser parser;

    /**
     * Instantiate a user interface.
     */
    public UserInterface() {
        this.parser = new ArgParser.Builder()
                .setPrefix(ARGUMENT_PREFIX)
                .addCommand(ENCODE_COMMAND)
                .addCommand(DECODE_COMMAND)
                .addCommand(ANALYZE_COMMAND)
                .addList(SOURCE_ARGUMENT, false)
                .addList(TARGET_ARGUMENT, true)
                .addBoolean(COMPRESS_ARGUMENT)
                .addVariable(PASSWORD_ARGUMENT, false)
                .build();
    }

    /**
     * Run the user interface on the provided arguments.
     * @param args the arguments.
     * @return the parsed parameters.
     */
    public Parameters parse(String... args) {
        Parameters parameters = parser.parse(args);
        assertValid(parameters);
        return parameters;
    }

    /**
     * Throw an IllegalArgumentException if the parameters are invalid.
     * @param parameters the parameters.
     */
    private void assertValid(Parameters parameters) {
        switch (parameters.command()) {
            case ANALYZE_COMMAND -> {
                if (parameters.has(SOURCE_ARGUMENT)) {
                    throw new IllegalArgumentException("The --source argument is not used for analyze.");
                }
                if (parameters.has(COMPRESS_ARGUMENT)) {
                    throw new IllegalArgumentException("The --compress argument is not used for analyze.");
                }
                if (parameters.has(PASSWORD_ARGUMENT)) {
                    throw new IllegalArgumentException("The --password argument is not used for analyze.");
                }
            }
            case ENCODE_COMMAND -> {
                if (!parameters.has(SOURCE_ARGUMENT)) {
                    throw new IllegalArgumentException("The --source argument is required when encoding.");
                }
                if (parameters.get(SOURCE_ARGUMENT).length != 1) {
                    throw new IllegalArgumentException("One file path is required for the --source argument when encoding.");
                }
                if (parameters.get(TARGET_ARGUMENT).length == 0) {
                    throw new IllegalArgumentException("At least one file path is required for the --target argument when encoding.");
                }
                if (!parameters.has(SOURCE_ARGUMENT)) {
                    throw new IllegalArgumentException("The --source argument is required for encoding and decoding.");
                }
            }
            case DECODE_COMMAND -> {
                if (!parameters.has(SOURCE_ARGUMENT)) {
                    throw new IllegalArgumentException("The --source argument is required when decoding.");
                }
                if (parameters.get(SOURCE_ARGUMENT).length == 0) {
                    throw new IllegalArgumentException("At least one file path is required for the --source argument when decoding.");
                }
                if (parameters.get(TARGET_ARGUMENT).length != 1) {
                    throw new IllegalArgumentException("One file path is required for the --target argument when decoding.");
                }
                if (!parameters.has(SOURCE_ARGUMENT)) {
                    throw new IllegalArgumentException("The --source argument is required for encoding and decoding.");
                }
            }
        }
    }
}
