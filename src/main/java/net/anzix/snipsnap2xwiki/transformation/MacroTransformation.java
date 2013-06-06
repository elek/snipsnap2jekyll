package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;

/**
 * Transform snipsnap macro
 */
public abstract class MacroTransformation extends RegExpTransformation {

    public static final String PATTERN = "\\{([^\\:\\}\\{]+)(?:\\:([^\\}\\n]*))?\\}";

    private String type;

    public MacroTransformation(String type) {
        super(PATTERN);
        this.type = type;
    }

    @Override
    public String replace(Matcher m, Page page) {
        if (m.group(1).trim().equals(type)) {
            if (m.groupCount() > 1 && m.group(2) != null) {
                return replace(m.group(2).split("\\|"), page);
            } else {
                return replace(new String[0], page);
            }
        }
        return m.group();
    }

    public abstract String replace(String args[], Page page);
}
