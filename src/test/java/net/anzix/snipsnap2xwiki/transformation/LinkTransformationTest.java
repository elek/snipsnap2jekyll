package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

import java.util.regex.Matcher;

public class LinkTransformationTest {

    @Test
    public void testReplace() throws Exception {
        LinkTransformation t = new LinkTransformation();

        Assert.assertEquals("qwe [index](http://index.hu)", t.transform("qwe {link:index|http://index.hu}", new Page()));
        Assert.assertEquals("qwe [http://index.hu](http://index.hu)", t.transform("qwe {link:http://index.hu}",new Page()));
        Assert.assertEquals("qwe [index](http://index.hu) vagy [444](http://444.hu)", t.transform("qwe {link:index|http://index.hu} vagy {link:444|http://444.hu}",new Page()));
        Assert.assertEquals("qwe {code}\n{asd} {code} qwe", t.transform("qwe {code}\n{asd} {code} qwe",new Page()));

    }
}
