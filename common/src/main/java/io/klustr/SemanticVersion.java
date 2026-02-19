package io.klustr;

/**
 * Wraps the knowledge of versioning and increment and comparison.
 */
public class SemanticVersion implements Comparable<SemanticVersion> {
    private int major;
    private int minor;
    private int patch;

    /**
     * Constructor that parses the semantic version string.
     *
     * @param version The version string in the format "major.minor.patch" (e.g., "1.2.3").
     * @throws IllegalArgumentException if the version format is invalid.
     */
    public SemanticVersion(String version) {
        String[] parts = version.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid version format. Expected format: major.minor.patch");
        }
        this.major = Integer.parseInt(parts[0]);
        this.minor = Integer.parseInt(parts[1]);
        this.patch = Integer.parseInt(parts[2]);
    }

    public static SemanticVersion parse(String version) {
        return new SemanticVersion(version);
    }

    public boolean isMajor() {
        return this.minor == 0 && this.patch == 0;
    }

    public SemanticVersion getMajorVersion() {
        return new SemanticVersion(this.major + ".0.0");
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public static enum Version {
        Minor,
        Major
    }

    /**
     * Increments the semantic version.
     */
    public SemanticVersion increment(Version type) {
        if (type == Version.Major) {
            // Increment the major version and reset minor and patch
            major += 1;
            minor = 0;
            patch = 0;
        } else {
            // Increment the patch version first
            patch += 1;
            if (patch > 9) {
                // If patch exceeds 9, reset it and increment the minor version
                patch = 0;
                minor += 1;
                if (minor > 9) {
                    // If minor exceeds 9, reset it and increment the major version
                    minor = 0;
                    major += 1;
                }
            }
        }
        return this;
    }

    /**
     * Decrements the semantic version.
     *
     * @param majorChange Boolean indicating if the change is a major decrement (true) or a minor/patch decrement (false).
     * @throws IllegalStateException if trying to decrement below version 0.0.0.
     */
    public SemanticVersion decrement(boolean majorChange) {
        if (majorChange) {
            // Decrement the major version and reset minor and patch
            if (major == 0) {
                throw new IllegalStateException("Cannot decrement below version 0.0.0");
            }
            major -= 1;
            minor = 9;
            patch = 9;
        } else {
            // Decrement the patch version first
            patch -= 1;
            if (patch < 0) {
                // If patch goes below 0, set it to 9 and decrement the minor version
                patch = 9;
                minor -= 1;
                if (minor < 0) {
                    // If minor goes below 0, set it to 9 and decrement the major version
                    minor = 9;
                    if (major == 0) {
                        throw new IllegalStateException("Cannot decrement below version 0.0.0");
                    }
                    major -= 1;
                }
            }
        }
        return this;
    }

    @Override
    public int compareTo(SemanticVersion other) {
        if (this.major != other.major) {
            return Integer.compare(this.major, other.major);
        }
        if (this.minor != other.minor) {
            return Integer.compare(this.minor, other.minor);
        }
        return Integer.compare(this.patch, other.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SemanticVersion other = (SemanticVersion) obj;
        return this.major == other.major && this.minor == other.minor && this.patch == other.patch;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(major);
        result = 31 * result + Integer.hashCode(minor);
        result = 31 * result + Integer.hashCode(patch);
        return result;
    }

    public String string() {
        return this.toString();
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    // Custom comparison methods
    public boolean isGreaterThanOrEqualTo(SemanticVersion other) {
        return this.compareTo(other) >= 0;
    }

    public boolean isLessThanOrEqualTo(SemanticVersion other) {
        return this.compareTo(other) <= 0;
    }

    public boolean isGreaterThan(SemanticVersion other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLessThan(SemanticVersion other) {
        return this.compareTo(other) < 0;
    }
}