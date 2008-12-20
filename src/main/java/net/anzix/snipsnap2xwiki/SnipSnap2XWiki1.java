/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import net.anzix.snipsnap2xwiki.transformation.Transformation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author elek
 */
public class SnipSnap2XWiki1 implements Transformation {

    private MigrationContext context;

    public SnipSnap2XWiki1(MigrationContext context) {
        this.context = context;
    }

    @Override
    public String transform(String source) {

        Matcher m = Pattern.compile("\\[([a-zA-Z0-9\\/| \\+\\.\\-]+)\\]").matcher(source);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {

            String originalName = m.group(1);
            String label = "";
            if (originalName.contains("|")) {
                int ix = originalName.indexOf("|");
                label = originalName.substring(0, ix);
                originalName = originalName.substring(ix);
            }
            if (context.getSnipNameCache().containsKey(originalName.toLowerCase())) {
                originalName = context.getSnipNameCache().get(originalName.toLowerCase());
            }
            String linkName = originalName.replaceAll("/", "");

            linkName = linkName.replaceAll("\\.", "\\\\\\\\\\.");
            if (context.getUserCache().keySet().contains(originalName)) {
                linkName = "XWiki." + linkName;
            }

            m.appendReplacement(sb, "[" + label + linkName + "]");

        }
        m.appendTail(sb);

        return sb.toString();
    }
}
