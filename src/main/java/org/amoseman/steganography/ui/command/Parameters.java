package org.amoseman.steganography.ui.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates a set of parsed parameters.
 */
public class Parameters {
    private final String command;
    private final Map<String, String[]> arguments;

    /**
     * Instantiate a set of parameters.
     * @param command the command.
     * @param arguments the arguments.
     */
    private Parameters(String command, Map<String, String[]> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * Get the command.
     * @return the command.
     */
    public String command() {
        return command;
    }

    /**
     * Check if a parameter exists.
     * @param name the name of the parameter.
     * @return true if it exists, false otherwise.
     */
    public boolean has(String name) {
        return arguments.containsKey(name);
    }

    /**
     * Get the value of a parameter.
     * @param name the name of the parameter.
     * @return the value.
     */
    public String[] get(String name) {
        String[] values = arguments.get(name);
        if (values == null) {
            throw new RuntimeException(String.format("%s is a boolean value", name));
        }
        return values;
    }

    /**
     * A builder for Parameter.
     */
    public static class Builder {
        private String command;
        private Map<String, String[]> arguments;

        /**
         * Instantiate a builder.
         */
        public Builder() {
            this.arguments = new HashMap<>();
        }

        /**
         * Throw an IllegalArgumentException if the argument is already set.
         * @param name the name of the argument.
         */
        private void assertUnique(String name) {
            if (arguments.containsKey(name)) {
                throw new IllegalArgumentException(String.format("Argument %s is already set.", name));
            }
        }

        /**
         * Set the command.
         * @param command the command.
         * @return this.
         */
        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }

        /**
         * Add a boolean argument.
         * @param name the name of the argument.
         * @throws IllegalArgumentException if the argument is already set.
         * @return this.
         */
        public Builder add(String name) {
            assertUnique(name);
            arguments.put(name, null);
            return this;
        }

        /**
         * Add an argument.
         * @param name the name of the argument.
         * @param value the value of the argument.
         * @throws IllegalArgumentException if the argument is already set.
         * @return this.
         */
        public Builder add(String name, String[] value) {
            assertUnique(name);
            arguments.put(name, value);
            return this;
        }

        /**
         * Build the parameters.
         * @throws RuntimeException if the command was not set.
         * @return the parameters.
         */
        public Parameters build() {
            if (command == null) {
                throw new RuntimeException("Command was not set.");
            }
            return new Parameters(command, arguments);
        }
    }
}
