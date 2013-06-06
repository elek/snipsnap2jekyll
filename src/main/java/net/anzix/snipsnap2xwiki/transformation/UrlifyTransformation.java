package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;

/**
 * Create links from the URLs.
 */
public class UrlifyTransformation extends RegExpTransformation {

    public UrlifyTransformation() {
        super("(?<!\\[)(?<!\\()((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
    }

    @Override
    public String replace(Matcher m, Page page) {
        return "[" + m.group(1) + "](" + m.group(1) + ")";
    }
}
