package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class ApiMacroTransformationTest {
    @Test
    public void testReplace() throws Exception {
        Assert.assertEquals("[java.text.SimpleDateFormat](http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)",new ApiMacroTransformation().transform("{api:java.text.SimpleDateFormat}",new Page()));
    }
}
