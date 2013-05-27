package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class TitleExtractorTest {

    @Test
    public void testReplace() throws Exception {
        TitleExtractor t = new TitleExtractor();
        Page p = new Page();
        Assert.assertEquals("qweqwe", t.transform("1 Title\n\nqweqwe", p));
        Assert.assertEquals("Title", p.getMetadata("title").toString());

        Assert.assertEquals("asd asd\n1 Titlex\n\nqweqwe", t.transform("asd asd\n1 Titlex\n\nqweqwe", p));
        Assert.assertEquals("Title", p.getMetadata("title").toString());

    }
}
