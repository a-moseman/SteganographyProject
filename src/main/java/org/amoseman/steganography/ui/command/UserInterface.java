package org.amoseman.steganography.ui.command;

import org.amoseman.steganography.processing.parameters.ProcessingType;
import org.amoseman.steganography.processing.parameters.ProcessingParameters;

import java.util.Optional;

public class UserInterface {
    public static final String ANALYZE = "analyze";
    public static final String ENCODE = "encode";
    public static final String DECODE = "decode";
    public static final String ARG_PREFIX = "--";
    public static final String COMPRESSION_ARG = ARG_PREFIX + "compress";
    public static final String PASSWORD_ARG = ARG_PREFIX + "password";
    public static final String SOURCE_ARG = ARG_PREFIX + "source";
    public static final String TARGET_ARG = ARG_PREFIX + "target";


    public static ProcessingType fromCommand(String command) {
        return switch (command) {
            case ANALYZE -> ProcessingType.ANALYZE;
            case ENCODE -> ProcessingType.ENCODE;
            case DECODE -> ProcessingType.DECODE;
            default -> throw new IllegalArgumentException(String.format("Unknown command %s", command));
        };
    }

    public ProcessingParameters run(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided.");
        }
        String command = args[0];
        ProcessingParameters.Builder builder = new ProcessingParameters.Builder();
        ProcessingType type = fromCommand(command);
        args = reduce(1, args);

        int index = 0;

        builder.setType(type);

        while (index < args.length) {
            String arg = args[index];
            if (arg.startsWith(ARG_PREFIX)) {
                index = applyArg(args, index, arg, builder);
            }
        }
        return builder.build();
    }

    public int applyArg(String[] args, int index, String arg, ProcessingParameters.Builder builder) {
        return switch (arg) {
            case COMPRESSION_ARG -> {
                builder.setCompression(true);
                yield index + 1;
            }
            case PASSWORD_ARG -> {
                if (index + 1 == args.length) {
                    throw new IllegalArgumentException("Expected password");
                }
                builder.setPassword(Optional.of(args[index + 1]));
                yield index + 2;
            }
            case SOURCE_ARG -> {
                if (index + 1 == args.length) {
                    throw new IllegalArgumentException("Expected source path(s)");
                }
                String[] source = argsTillNextTag(args, index + 1);
                builder.setSource(source);
                yield index + source.length + 1;
            }
            case TARGET_ARG -> {
                if (index + 1 == args.length) {
                    throw new IllegalArgumentException("Expected target path(s)");
                }
                String[] target = argsTillNextTag(args, index + 1);
                builder.setTarget(target);
                yield index + target.length + 1;
            }
            default -> throw new IllegalArgumentException(String.format("Unknown argument %s", arg));
        };
    }

    public String[] argsTillNextTag(String[] args, int start) {
        int end = args.length;
        for (int i = start; i < args.length; i++) {
            if (args[i].startsWith(ARG_PREFIX)) {
                end = i;
                break;
            }
        }
        String[] sub = new String[end - start];
        System.arraycopy(args, start, sub, 0, sub.length);
        return sub;
    }

    public String[] reduce(int n, String... args) {
        if (args.length - n < 0) {
            throw new IllegalArgumentException("Can not reduce arguments to length less than 0.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Can not reduce by a negative value.");
        }
        String[] reduced = new String[args.length - n];
        System.arraycopy(args, n, reduced, 0, reduced.length);
        return reduced;
    }
}
