package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Search and replace based on a regexp.
 */
public abstract class RegExpTransformation implements Transformation {
    Pattern pattern;

    public RegExpTransformation(String ptr) {
        pattern = Pattern.compile(ptr, Pattern.MULTILINE);

    }

    @Override
    public String transform(String source, Page page) {
        Matcher m = pattern.matcher(source);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, Matcher.quoteReplacement(replace(m, page)));
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public abstract String replace(Matcher m, Page page);

    public Pattern getPattern() {
        return pattern;
    }
}
