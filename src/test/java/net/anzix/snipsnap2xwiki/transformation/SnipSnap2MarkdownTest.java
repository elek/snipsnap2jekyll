/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.TestCase;
import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author elek
 */
public class SnipSnap2MarkdownTest extends TestCase {

    public SnipSnap2MarkdownTest(String testName) {
        super(testName);
    }

    /**
     * Test of transform method, of class SnipSnap2MarkdownTest.
     */
    @Test
    public void testPattern() {
        String test = "asd [Oracle]wq q\nvalami [masik]x[harmadik]xx\n [negyedik] ";
        Matcher m = Pattern.compile("\\[([a-zA-Z \\+]+)\\]").matcher(test);
        while (m.find()) {
            System.out.println("FOUND:" + m.group(1));
        }
    }

    /**
     * Test of transform method, of class SnipSnap2MarkdownTest.
     */
    @Test
    public void testTransform() {

        MigrationContext context = new MigrationContext(new File("build/test"));
        context.getUserCache().put("asd", null);
        context.getSnipNameCache().put("oracle", "Oracle");
        context.getSnipNameCache().put("j2ee", "j2ee");
        context.getSnipNameCache().put("java 1.5", "java 1.5");
        context.getSnipNameCache().put("qwe", "qwe");
        context.getSnipNameCache().put("test/asd", "test/asd");
        context.getSnipNameCache().put("sun developer day", "sun developer day");
        net.anzix.snipsnap2xwiki.transformation.SnipSnap2Markdown transform = new net.anzix.snipsnap2xwiki.transformation.SnipSnap2Markdown(context);

        Page p = new Page();
        p.addMeta("path","test/qwe");
        assertEquals("valami [oracle](../Oracle.html) valami", transform.transform("valami [oracle] valami", p));

        assertEquals("valami [oracle](Oracle.html) valami [J2EE](j2ee.html)", transform.transform("valami [oracle] valami [J2EE]", new Page()));
        assertEquals("valami [java 1.5](java%201.5.html)", transform.transform("valami [java 1.5]", new Page()));
        assertEquals("valami [test](qwe.html) e", transform.transform("valami [test|qwe] e", new Page()));
        assertEquals("valami [test/asd](test/asd.html) e", transform.transform("valami [test/asd] e", new Page()));
        assertEquals(" a [sun fejlesztői nap](sun%20developer%20day.html),", transform.transform(" a [sun fejlesztői nap|sun developer day],", new Page()));

        assertEquals("qwe<br/>\nesd", transform.transform("qwe\\\\\nesd", new Page()));

        assertEquals("[no](Missing.html)", transform.transform("[no]", new Page()));
        assertTrue(context.getMissingPages().contains("no"));
    }
}
