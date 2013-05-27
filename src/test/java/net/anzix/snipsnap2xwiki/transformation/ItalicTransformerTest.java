package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class ItalicTransformerTest {
    @Test
    public void testReplace() throws Exception {
        ItalicTransformer i = new ItalicTransformer();
        Assert.assertEquals("a _asd_ b", i.transform("a ~~asd~~ b", new Page()));
    }
}
