package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class LineEndAndListTransformationTest {
    @Test
    public void testTransform() throws Exception {
        Page p = new Page();
        Transformation t = new LineEndAndListTransformation();
        Assert.assertEquals("1\n2\n\n# title\n\n4\n5\n", t.transform("1\n2\n# title\n\n4\n5\n", p));
        Assert.assertEquals("*   asd\n    *   qwe\n", t.transform(" - asd\n -- qwe\n", p));
        Assert.assertEquals("*   asd\n    *   qwe\n", t.transform(" * asd\n ** qwe\n", p));
        Assert.assertEquals("1.   asd\n1.   qwe\n", t.transform("1. asd\n1. qwe\n", p));


        String s = "a\nb\nc\n1. d\n1. e\n1. f\n\ng\n\nh\n\ni\n- j\n- k\n- l\n";
        String expected = "a\nb\nc\n\n1.   d\n1.   e\n1.   f\n\ng\n\nh\n\ni\n\n*   j\n*   k\n*   l\n";
        Assert.assertEquals(expected, t.transform(s, p));
    }
}
