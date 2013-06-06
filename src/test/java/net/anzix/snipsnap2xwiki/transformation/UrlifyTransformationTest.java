package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class UrlifyTransformationTest {
    @Test
    public void testReplace() throws Exception {
         UrlifyTransformation t = new UrlifyTransformation();
        Assert.assertEquals(" [http://444.hu/asd?qwe=1](http://444.hu/asd?qwe=1)\nqwe",t.transform(" http://444.hu/asd?qwe=1\nqwe",new Page()));

    }
}
