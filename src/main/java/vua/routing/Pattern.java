package vua.routing;

enum PatternType {
    STATIC,
    REGEX,
    PARAM,
    WILDCARD
}

public class Pattern {

    private String raw;
    private PatternType type;

    public Pattern(String raw) {
        this.raw = raw;
    }

    public String toString() {
        return this.raw;
    }
}
