package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Remove macros what shouldn't be migrated.
 */
public class RemoveMacrosTransformation extends RegExpTransformation {

    Set<String> ignoreList = new HashSet<>();

    public RemoveMacrosTransformation() {
        super(MacroTransformation.PATTERN);
        ignoreList.add("snip-xref");
        ignoreList.add("smip-xref");
        ignoreList.add("snips-by-user");
        ignoreList.add("label-search");
        ignoreList.add("field");
        ignoreList.add("weblog");
        ignoreList.add("graph");

        //TODO
        ignoreList.add("isbn");
        ignoreList.add("snip-xtree");

        ignoreList.add("api");
    }

    @Override
    public String replace(Matcher m, Page page) {
        if (ignoreList.contains(m.group(1).trim())) {
            return "";
        } else {
            return m.group();
        }
    }
}
