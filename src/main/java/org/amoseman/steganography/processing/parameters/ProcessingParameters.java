package org.amoseman.steganography.processing.parameters;

import java.util.Optional;

/**
 * Represents the parameters for processing.
 */
public class ProcessingParameters {
    public final ActionType type;
    public final boolean compression;
    public final Optional<String> password;
    public final String[] source;
    public final String[] target;


    /**
     * Instantiate parameters for processing.
     * @param type the type of processing to do.
     * @param compression whether to use compression or not.
     * @param password the password for encryption, or empty if not encryption is to be used.
     * @param source the source file paths that will be read from.
     * @param target the target file paths that will be written to.
     */
    private ProcessingParameters(ActionType type, boolean compression, Optional<String> password, String[] source, String[] target) {
        this.type = type;
        this.compression = compression;
        this.password = password;
        this.source = source;
        this.target = target;
    }

    public static class Builder {
        private ActionType type = null;
        private boolean compression = false;
        private Optional<String> password = Optional.empty();
        private String[] source = null;
        private String[] target = null;

        public Builder setType(ActionType type) {
            if (this.type != null) {
                throw new IllegalArgumentException("Processing type already set.");
            }
            this.type = type;
            return this;
        }

        public Builder setCompression(boolean compression) {
            if (this.compression) {
                throw new IllegalArgumentException("Compression already enabled.");
            }
            this.compression = compression;
            return this;
        }

        public Builder setPassword(Optional<String> password) {
            if (this.password.isPresent()) {
                throw new IllegalArgumentException("Password already set.");
            }
            this.password = password;
            return this;
        }

        public Builder setSource(String[] source) {
            if (this.source != null) {
                throw new IllegalArgumentException("Source file paths already set.");
            }
            this.source = source;
            return this;
        }

        public Builder setTarget(String[] target) {
            if (this.target != null) {
                throw new IllegalArgumentException("Target file paths already set.");
            }
            this.target = target;
            return this;
        }

        public ProcessingParameters build() {
            if (type == null) {
                throw new IllegalArgumentException("Processing type is not set.");
            }
            if (target == null) {
                throw new IllegalArgumentException("Target file path(s) are not set. Use the --target argument.");
            }
            if (target.length == 0) {
                throw new IllegalArgumentException("No path(s) were provided with the --target argument.");
            }
            if (type == ActionType.ANALYZE) {
                if (source != null) {
                    throw new IllegalArgumentException("The --source argument is not used with the analyze command.");
                }
                if (compression) {
                    throw new IllegalArgumentException("The --compression argument is not used with the analyze command.");
                }
                if (password.isPresent()) {
                    throw new IllegalArgumentException("The --password argument is not used with the analyze command.");
                }
            }
            else {
                if (source == null) {
                    throw new IllegalArgumentException("Source file path(s) are not set. Use the --source argument.");
                }
                if (source.length == 0) {
                    throw new IllegalArgumentException("No path(s) were provided with the --source argument");
                }
            }
            if (type == ActionType.ENCODE) {
                if (source.length > 1) {
                    throw new IllegalArgumentException("Encoding is one to many, but you provided multiple paths with --source.");
                }
            }
            if (type == ActionType.DECODE) {
                if (target.length > 1) {
                    throw new IllegalArgumentException("Decoding is many to one, but you provided multiple paths with --target.");
                }
            }
            return new ProcessingParameters(type, compression, password, source, target);
        }
    }
}
