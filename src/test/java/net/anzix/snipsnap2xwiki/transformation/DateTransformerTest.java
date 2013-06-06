package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;
import org.junit.Assert;
import org.junit.Test;

public class DateTransformerTest {

    @Test
    public void test() {
        DateTransformer formatter = new DateTransformer();
        Assert.assertEquals("2004-07-30 14:18:45 +0200", formatter.transform("1091189925295", new Page()));
    }
}
