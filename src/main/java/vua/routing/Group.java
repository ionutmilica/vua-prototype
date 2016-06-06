package vua.routing;

import vua.utils.StringUtil;

public class Group {

    private String prefix;

    public Group(String prefix) {
        this.prefix = prefix;
    }

    public void buildPrefix(StringBuilder builder) {
        if (prefix.length() < 1) {
            return;
        }

        if (builder.length() > 0 && !StringUtil.trim(prefix, '/').isEmpty()) {
            builder.append('/');
        }

        if (prefix.charAt(0) == '/' || prefix.charAt(prefix.length() - 1) == '/') {
            builder.append(StringUtil.trim(prefix, '/'));
        } else {
            builder.append(prefix);
        }
    }

    public String getPrefix() {
        return prefix;
    }
}
