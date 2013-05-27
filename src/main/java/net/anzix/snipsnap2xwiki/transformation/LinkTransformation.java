package net.anzix.snipsnap2xwiki.transformation;

/**
 * Replace snipsnap link macros.
 */
public class LinkTransformation extends MacroTransformation {

    public LinkTransformation() {
        super("link");
    }

    @Override
    public String replace(String[] args) {
        if (args.length == 1) {
            return "[" + args[0] + "](" + args[0] + ")";
        } else if (args.length == 2) {
            return "[" + args[0] + "](" + args[1] + ")";
        } else {
            throw new IllegalArgumentException("Link without ");
        }
    }
}
