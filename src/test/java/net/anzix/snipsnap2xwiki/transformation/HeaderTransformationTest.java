package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class HeaderTransformationTest {

    @Test
    public void testTransform() throws Exception {
        HeaderTransformation t = new HeaderTransformation();
        Assert.assertTrue(t.getPattern().matcher(" 1 test w").matches());
        Assert.assertEquals("# test w", t.transform(" 1 test w",new Page()));
        Assert.assertEquals("# test w\n\nqwe\n", t.transform(" 1 test w\n\nqwe\n",new Page()));

    }
}
