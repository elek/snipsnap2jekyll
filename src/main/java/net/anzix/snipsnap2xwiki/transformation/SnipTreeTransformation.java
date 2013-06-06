package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.Page;

/**
 * Transform snip-tree macros (list of sub pages).
 */
public class SnipTreeTransformation extends MacroTransformation {
    private MigrationContext context;

    public SnipTreeTransformation(MigrationContext context) {
        super("snip-tree");
        this.context = context;
    }

    @Override
    public String replace(String[] args, Page page) {
        if (args.length == 0) {
            return "";
        } else {
            String res = "\n";
            for (String path : context.getSnipNameCache().values()) {
                if (path.startsWith(args[0])) {
                    res += "-   [" + path + "](" +
                            SnipSnap2Markdown.relativePrefix((String) page.getMeta("title")) + path + ".html)\n";
                }
            }
            res += "\n";
            return res;
        }
    }
}
