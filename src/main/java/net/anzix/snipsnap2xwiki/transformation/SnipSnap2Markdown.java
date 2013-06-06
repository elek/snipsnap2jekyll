/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.Page;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert SnipSnap internal macros and do addtional cleanups.
 *
 * @author elek
 */
public class SnipSnap2Markdown implements Transformation {

    private MigrationContext context;

    public SnipSnap2Markdown(MigrationContext context) {
        this.context = context;
    }

    @Override
    public String transform(String source, Page page) {
        //small workaround
        //regex: \\
        source = source.replaceAll("\\\\\\\\", "<br/>");
        //TODO move out the jhacks specific improvements out.
        source = source.replaceAll("don`t", "don't");
        source = source.replaceAll("Wikipedia:", "Wikipedia");
        source = source.replaceAll("<age>", "&lt;age&gt;");
        source = source.replaceAll("\\*+t", "\\*\\*\\*\\*\\*t");
        source = source.replaceAll(Pattern.quote("Azaz: /**/ es /*/**/"), "Azaz: /\\*\\*/ es /\\*/\\*\\*/");


        //relace links
        Matcher m = Pattern.compile("\\[([^\\]]+)\\]").matcher(source);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {

            String originalName = m.group(1);
            String label = "";
            if (originalName.contains("|")) {
                int ix = originalName.indexOf("|");
                label = originalName.substring(0, ix);
                originalName = originalName.substring(ix + 1);
            } else {
                label = originalName;
            }
            if (context.getSnipNameCache().containsKey(originalName.toLowerCase())) {
                originalName = context.getSnipNameCache().get(originalName.toLowerCase());
            } else {
                context.addMissingPage(originalName);
                originalName = "Missing";
            }
            String linkName = originalName;

            //linkName = linkName.replaceAll("\\.", "\\\\\\\\\\.");
            if (context.getUserCache().keySet().contains(originalName)) {
                linkName = linkName;
            }
            linkName = relativePrefix((String) page.getMeta("path")) + linkName;
            m.appendReplacement(sb, "[" + label + "](" + URLEncoder.encode(linkName).
                    replace("+", "%20").replace("%2F", "/") + ".html)");

        }
        m.appendTail(sb);

        return sb.toString();
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    public static String relativePrefix(String name) {
        if (name == null) {

            return "";
        }
        String res = "";
        if (name != null) {
            int c = countOccurrences(name, '/');
            for (int i = 0; i < c; i++) {
                res = "../" + res;
            }
        }
        return res;
    }
}
