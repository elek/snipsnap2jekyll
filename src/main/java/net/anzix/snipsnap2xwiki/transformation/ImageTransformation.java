package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

/**
 * This class transforms images from format
 * <pre>
 * {image:img=WTF/hirig.gif}
 * </pre>
 * to <pre>
 * {image:hirig.gif}
 * </pre>
 *
 * @author kocka
 */
public class ImageTransformation extends MacroTransformation {


    public ImageTransformation() {
        super("image");
    }

    @Override
    public String replace(String[] args, Page page) {
        String image = "";
        String link = "";
        String alt = "image";
        for (String arg : args) {
            if (arg.startsWith("img=")) {
                image = arg.substring("img=".length());
            } else if (arg.startsWith("link=")) {
                link = arg.substring("link=".length());
            } else if (arg.startsWith("alt=")) {
                alt = arg.substring("alt=".length());
            } else if (arg.startsWith("http:")) {
                image = arg;
            } else if (arg.endsWith(".gif") || arg.endsWith(".png")) {
                image = arg;
            } else {
                throw new UnsupportedOperationException("Unknown image parameter: >" + arg + "<");
            }
        }
        return "![" + alt + "](" + image + ")" + (link.length() > 0 ? "(" + link + ")" : "");
    }
}
