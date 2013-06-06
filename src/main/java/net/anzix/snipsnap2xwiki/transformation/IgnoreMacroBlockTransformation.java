package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Traform code blocks
 */
public class IgnoreMacroBlockTransformation extends RegExpTransformation {

    /**
     * First phase
     */
    public IgnoreMacroBlockTransformation(String name) {
        super(".*");
        pattern = Pattern.compile("\\{" + name + "(?:[^\\}]+)?\\}(.+?)\\{" + name + "\\}", Pattern.DOTALL);
    }


    @Override
    public String replace(Matcher m, Page page) {
        return m.group(1);
    }

}
