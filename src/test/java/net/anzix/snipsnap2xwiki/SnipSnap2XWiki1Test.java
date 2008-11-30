/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.snipsnap2xwiki;

import java.util.HashMap;
import java.util.Map;
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
        while (m.find()){
            System.out.println("FOUND:"+m.group(1));
        }
    }

    /**
     * Test of transform method, of class SnipSnap2XWiki1.
     */
    public void testTransform() {
        Map<String,String> pages = new HashMap(){{
            put("oracle", "Oracle");
        }};
        SnipSnap2XWiki1 transform = new SnipSnap2XWiki1(pages);
        assertEquals("valami [Oracle] valami", transform.transform("valami [oracle] valami"));
    }

}
