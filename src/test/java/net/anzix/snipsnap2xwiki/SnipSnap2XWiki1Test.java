/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;

/**
 *
 * @author elek
 */
public class SnipSnap2XWiki1Test extends TestCase {

    public SnipSnap2XWiki1Test(String testName) {
        super(testName);
    }

    /**
     * Test of transform method, of class SnipSnap2XWiki1.
     */
    public void testPattern() {
        String test = "asd [Oracle]wq q\nvalami [masik]x[harmadik]xx\n [negyedik] ";
        Matcher m = Pattern.compile("\\[([a-zA-Z \\+]+)\\]").matcher(test);
        while (m.find()) {
            System.out.println("FOUND:" + m.group(1));
        }
    }

    /**
     * Test of transform method, of class SnipSnap2XWiki1.
     */
    public void testTransform() {

        MigrationContext context = new MigrationContext();
        context.getUserCache().put("asd", null);
        context.getSnipNameCache().put("oracle", "Oracle");
        context.getSnipNameCache().put("j2ee", "j2ee");
        SnipSnap2XWiki1 transform = new SnipSnap2XWiki1(context);
        assertEquals("valami [Oracle] valami [j2ee]", transform.transform("valami [oracle] valami [J2EE]"));
        assertEquals("valami [java 1\\.5]", transform.transform("valami [java 1.5]"));
    }
}
