/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author elek
 */
public class SnipSnap2XWiki1 implements Transformation {

    private Map<String, String> snipNameCache;

    public SnipSnap2XWiki1(Map<String, String> snipNameCache) {
        this.snipNameCache = snipNameCache;
    }

    @Override
    public String transform(String source) {

        Matcher m = Pattern.compile("\\[([a-zA-Z \\+]+)\\]").matcher(source);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            String group = m.group(1);
            if (snipNameCache.containsKey(group.toLowerCase())) {
                m.appendReplacement(sb, "["+snipNameCache.get(group.toLowerCase())+"]");
            }
        }
        m.appendTail(sb);

        return sb.toString();
    }
}
