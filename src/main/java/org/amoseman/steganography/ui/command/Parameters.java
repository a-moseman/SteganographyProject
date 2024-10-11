package org.amoseman.steganography.ui.command;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private final String command;
    private final Map<String, String[]> arguments;

    private Parameters(String command, Map<String, String[]> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String command() {
        return command;
    }

    public boolean has(String name) {
        return arguments.containsKey(name);
    }

    public String[] get(String name) {
        String[] values = arguments.get(name);
        if (values == null) {
            throw new RuntimeException(String.format("%s is a boolean value", name));
        }
        return values;
    }

    public static class Builder {
        private String command;
        private Map<String, String[]> arguments;

        public Builder() {
            this.arguments = new HashMap<>();
        }

        private void assertUnique(String name) {
            if (arguments.containsKey(name)) {
                throw new IllegalArgumentException(String.format("Argument %s is already set.", name));
            }
        }

        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }

        public Builder add(String name) {
            assertUnique(name);
            arguments.put(name, null);
            return this;
        }

        public Builder add(String name, String[] values) {
            assertUnique(name);
            arguments.put(name, values);
            return this;
        }

        public Parameters build() {
            if (command == null) {
                throw new RuntimeException("Command was not set.");
            }
            return new Parameters(command, arguments);
        }
    }
}
