package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

/**
 * Transform {api:java.util.Vector} snip macro to a link to the javadoc api page.
 */
public class ApiMacroTransformation extends MacroTransformation {

    public ApiMacroTransformation() {
        super("api");
    }

    @Override
    public String replace(String[] args, Page page) {
        return "[" + args[0] + "](http://docs.oracle.com/javase/7/docs/api/" +
                args[0].replaceAll("\\.", "/") + ".html)";
    }
}
