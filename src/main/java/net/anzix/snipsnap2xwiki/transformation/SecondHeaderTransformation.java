package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;

/**
 * Transform snipsnap style header to markdown one.
 */
public class SecondHeaderTransformation extends RegExpTransformation {

    public SecondHeaderTransformation() {
        super("^\\s*1\\.1\\s+(.*)$");
    }

    @Override
    public String replace(Matcher m, Page page) {
        return "## " + m.group(1);
    }
}
