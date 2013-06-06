package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;

/**
 * throw an exception if any macro is still in the text
 */
public class NoMacroTransformation extends MacroTransformation {

    public NoMacroTransformation() {
        super("none");
    }

    @Override
    public String replace(Matcher m, Page page) {
        if (!m.group(1).trim().startsWith("%") && m.group(1).trim().length() > 0) {
            throw new IllegalArgumentException("Unhandled macro: " + m.group(1).trim());
        }
        return m.group();
    }

    @Override
    public String replace(String[] args, Page page) {
        return null;
    }
}
