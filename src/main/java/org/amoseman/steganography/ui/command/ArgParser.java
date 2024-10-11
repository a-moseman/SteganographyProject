package org.amoseman.steganography.ui.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgParser {
    public static final int BOOLEAN = 0;
    public static final int VARIABLE = 1;
    public static final int LIST = -1;
    private final String prefix;
    private final Set<String> commands;
    private final Map<String, Integer> parameters;
    private final Set<String> required;

    private ArgParser(String prefix, Set<String> commands, Map<String, Integer> parameters, Set<String> required) {
        this.prefix = prefix;
        this.commands = commands;
        this.parameters = parameters;
        this.required = required;
    }

    public static String[] reduce(String... args) {
        int size = args.length - 1;
        if (size < 0) {
            throw new IllegalArgumentException("Can not reduce arguments to length less than 0.");
        }
        String[] reduced = new String[size];
        System.arraycopy(args, 1, reduced, 0, size);
        return reduced;
    }

    public Parameters parse(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided.");
        }

        String command = args[0];
        if (!commands.contains(command)) {
            StringBuilder message = new StringBuilder();
            message.append(String.format("Command %s is not valid.\n", command));
            message.append("Instead, use one of the following:\n");
            commands.forEach(cmd -> message.append(String.format("\t%s\n", cmd)));
            throw new IllegalArgumentException(message.toString());
        }

        Parameters.Builder builder = new Parameters.Builder()
                .setCommand(command);
        args = reduce(args);

        int index = 0;
        while (index < args.length) {
            String arg = args[index];
            index = parseArgument(arg, index, args, builder);
        }
        Parameters parameters = builder.build();
        for (String req : required) {
            if (parameters.has(req)) {
                continue;
            }
            throw new IllegalArgumentException(String.format("%s is a required argument.", req));
        }
        return parameters;
    }

    private int parseArgument(String arg, int index, String[] args, Parameters.Builder builder) {
        String name = arg.replaceFirst(prefix, "");
        if (!parameters.containsKey(name)) {
            throw new IllegalArgumentException(String.format("%s is not a recognized argument", arg));
        }
        int valueCount = parameters.get(name);
        return switch (valueCount) {
            case BOOLEAN -> parseBooleanArgument(name, index, builder);
            case VARIABLE -> parseVariableArgument(name, index, args, builder);
            case LIST -> parseListArgument(name, index, args, builder);
            default -> parseArrayArgument(name, index, args, builder, valueCount);
        };
    }

    private int parseBooleanArgument(String arg, int index, Parameters.Builder builder) {
        builder.add(arg);
        return index + 1;
    }

    private int parseVariableArgument(String arg, int index, String[] args, Parameters.Builder builder) {
        if (args.length == index + 1) {
            throw new IllegalArgumentException(String.format("Argument %s missing a value.", arg));
        }
        builder.add(arg, new String[]{args[index + 1]});
        return index + 2;
    }

    private int parseListArgument(String arg, int index, String[] args, Parameters.Builder builder) {
        int start = index + 1;
        int end = args.length;
        for (int i = start; i < args.length; i++) {
            if (parameters.containsKey(args[i].replaceFirst(prefix, ""))) {
                end = i;
                break;
            }
        }
        int size = Math.max(0, end - start);
        String[] values = new String[size];
        System.arraycopy(args, start, values, 0, values.length);
        builder.add(arg, values);
        return index + values.length + 1;
    }

    private int parseArrayArgument(String arg, int index, String[] args, Parameters.Builder builder, int valueCount) {
        if (index + valueCount >= args.length) {
            throw new IllegalArgumentException(String.format("Argument %s requires %d values.", arg, valueCount));
        }
        String[] values = new String[valueCount];
        for (int i = 0; i < valueCount; i++) {
            values[i] = args[i + index + 1];
        }
        builder.add(arg, values);
        return index + values.length + 1;
    }

    public static class Builder {
        private String prefix;
        private Set<String> commands;
        private Map<String, Integer> parameters;
        private Set<String> required;

        public Builder() {
            this.commands = new HashSet<>();
            this.parameters = new HashMap<>();
            this.required = new HashSet<>();
        }

        public Builder setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder addCommand(String command) {
            commands.add(command);
            return this;
        }

        private Builder addParameter(String name, int size, boolean isRequired) {
            if (prefix == null) {
                throw new RuntimeException("Prefix not set.");
            }
            parameters.put(name, size);
            if (isRequired) {
                required.add(name);
            }
            return this;
        }

        public Builder addBoolean(String name) {
            return addParameter(name, BOOLEAN, false);
        }

        public Builder addVariable(String name, boolean isRequired) {
            return addParameter(name, VARIABLE, isRequired);
        }

        public Builder addArray(String name, int size, boolean isRequired) {
            return addParameter(name, size, isRequired);
        }

        public Builder addList(String name, boolean isRequired) {
            return addParameter(name, LIST, isRequired);
        }

        public ArgParser build() {
            return new ArgParser(prefix, commands, parameters, required);
        }
    }
}
