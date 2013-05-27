package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;

/**
 * Transform ~~texts~~
 */
public class ItalicTransformer extends RegExpTransformation {
    public ItalicTransformer() {
        super("~~(.*?)~~");
    }

    @Override
    public String replace(Matcher m, Page page) {
        return "_" + m.group(1) + "_";
    }
}
