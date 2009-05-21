/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.TestCase;

/**
 *
 * @author elek
 */
public class MacroListTransformationTest extends TestCase {

    public MacroListTransformationTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of transform method, of class MacroListTransformation.
     */
    public void testTransform() {
        String input = "asd \n{snip-xref:ASF|100}\n{link:asd|asd}basd";
        MacroListTransformation transformation = new MacroListTransformation();
        transformation.transform(input);

        for(String macro:transformation.macros){
            System.out.println(macro);
        }
    }
}
