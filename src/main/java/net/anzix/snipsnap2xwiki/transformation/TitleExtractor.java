package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Use the beginnig h1 as the title of the page.
 */
public class TitleExtractor extends RegExpTransformation {

    public TitleExtractor() {
        super(".*");
        //NO multiline
        pattern = Pattern.compile("^\\W*1\\s*([^\\n]+)\\n+(.*)");
    }

    @Override
    public String replace(Matcher m, Page page) {
        page.addMeta("title", m.group(1));
        return m.group(2);
    }
}
