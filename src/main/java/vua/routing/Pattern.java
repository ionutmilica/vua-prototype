package vua.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

enum PatternType {
    STATIC,
    REGEX,
    PARAM,
    WILDCARD
}

public class Pattern {

    private String raw;
    private String clean;
    private PatternType type;
    private boolean isOptional;
    private ArrayList<String> wildcards;

    // Regex pattern
    private String compiled;
    private java.util.regex.Pattern regex;


    public Pattern(String pattern) {
        raw = pattern;
        type = PatternType.STATIC;
        wildcards = new ArrayList<>();
        isOptional = false;
        compiled = "";

        compile(raw);
    }

    private void compile(String pattern) {
        StringBuilder builder = new StringBuilder();
        HashMap<String, String> wildcards = new HashMap<>();
        boolean inWildcard = false;
        int start = 0;

        java.util.regex.Pattern r = java.util.regex.Pattern.compile("^\\{[^\\{\\}:]+\\}$");
        Matcher m = r.matcher(raw);

        if (m.matches()) {
            int backWords = 1;

            if (raw.charAt(raw.length() - 2) == '?') {
                isOptional = true;
                backWords = 2;
            }

            String name;

            name = raw.substring(1, raw.length() - backWords);
            type = PatternType.PARAM;
            this.wildcards.add(name);
            clean = "{"+name+"}";

            return;
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (inWildcard) {
                if (pattern.charAt(i) == '}') {
                    inWildcard = false;
                    String wildcard = pattern.substring(start + 1, i);

                    String[] parts = wildcard.split(":");

                    if (parts.length > 2) {
                        throw new RuntimeException("`" + wildcard + "` contains more than 1 modifier (:) in `" + pattern + "` pattern.");
                    }

                    if (parts.length > 1) {
                        wildcards.put("{"+wildcard+"}", "(?<" + parts[0] + ">" + parts[1] + ")");
                    } else {
                        wildcards.put("{"+wildcard+"}", "(?<" + parts[0] + ">.+)");
                    }

                    builder.append('{');
                    builder.append(parts[0]);
                    builder.append('}');
                    this.wildcards.add(parts[0]);
                } else if (pattern.charAt(i) == '{') {
                    throw new RuntimeException("`" + pattern + "` pattern is invalid and cannot be compiled!");
                }
            } else {
                if (pattern.charAt(i) == '{') {
                    inWildcard = true;
                    start = i;
                    continue;
                }
                if (pattern.charAt(i) == '}') {
                    throw new RuntimeException("`" + pattern + "` pattern is invalid and cannot be compiled!");
                }
                builder.append(pattern.charAt(i));
            }
        }

        for (Map.Entry<String, String> entry : wildcards.entrySet()) {
            pattern = pattern.replace(entry.getKey(), entry.getValue());
        }

        if ( ! raw.equals(pattern)) {
            type = PatternType.REGEX;
            compiled = "^" + pattern + "$";
            regex = java.util.regex.Pattern.compile(compiled);
        }
        clean = builder.toString();
    }

    /**
     * Match the pattern against a pattern
     *
     * @param against
     * @return
     */
    public MatchResult match(String against) {
        MatchResult matcher = new MatchResult();
        switch (type) {
            case STATIC:
                if (against.equals(raw)) {
                    matcher.setMatched(true);
                }
                break;
            case REGEX:
                Matcher regMatcher = regex.matcher(against);
                if (regMatcher.matches()) {
                    matcher.setMatched(true);
                    HashMap<String, String> result = matcher.getResult();
                    for (String wildcard : wildcards) {
                        result.put(wildcard, regMatcher.group(wildcard));
                    }
                }
                break;
            case PARAM:
                matcher.setMatched(true);
                HashMap<String, String> result = matcher.getResult();
                result.put(wildcards.get(0), against);
                break;
        }

        return matcher;
    }

    /**
     * Check if the pattern has optional modifier "?"
     *
     * @return boolean
     */
    public boolean isOptional() {
        return isOptional;
    }

    /**
     * Get the pattern type
     *
     * STATIC
     * REGEX
     * PARAM
     * WILDCARD
     *
     * @return Pattern type
     */
    public PatternType getType() {
        return type;
    }

    /**
     * Get the pattern after the compilation
     * If it contains regex the result should be a regex expression else, just the raw pattern
     *
     * @return Compiled pattern
     */
    public String getCompiled() {
        return compiled.isEmpty() ? raw : compiled;
    }

    public String toString() {
        return this.raw;
    }

    public String getClean() {
        return clean;
    }
}
