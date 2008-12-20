/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.snipsnap2xwiki;

import junit.framework.TestCase;

/**
 *
 * @author elek
 */
public class DomCopierTest extends TestCase {
    
    public DomCopierTest(String testName) {
        super(testName);
    }

    public void testParseObjectPath(){
        String[] result = DomCopier.parseObjectPath("XWiki.Class[0]");
        assertEquals("XWiki.Class",result[0]);
        assertEquals("0",result[1]);

        result = DomCopier.parseObjectPath("XWiki.Class[1]");
        assertEquals("XWiki.Class",result[0]);
        assertEquals("1",result[1]);

        result = DomCopier.parseObjectPath("XWiki.Class");
        assertEquals("XWiki.Class",result[0]);
        assertEquals("0",result[1]);
    }

}
