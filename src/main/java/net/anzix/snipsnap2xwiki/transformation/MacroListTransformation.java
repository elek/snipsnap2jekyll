/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author elek
 */
public class MacroListTransformation implements Transformation {

    public static final String PATTERN = "\\{([\\w\\-]+?)\\:(.+?)\\}";

    private Pattern pattern;

    public static Set<String> macros = new HashSet();

    public MacroListTransformation() {
        pattern = Pattern.compile(PATTERN, Pattern.DOTALL);
    }

    @Override
    public String transform(String source) {

        Matcher m = pattern.matcher(source);
        StringBuffer sbuf = new StringBuffer();
        while (m.find()) {
            macros.add(m.group(1));
          //  m.appendReplacement(sbuf, "");
        }
        //m.appendTail(sbuf);
        return source;

    }
}
