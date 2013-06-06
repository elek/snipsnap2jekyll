package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.MigrationContext;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class SnipTreeTransformationTest {
    @Test
    public void testReplace() throws Exception {
        MigrationContext c = new MigrationContext(new File("."));
        c.getSnipNameCache().put("wtf/qwe", "WTF/qwe");
        c.getSnipNameCache().put("wtf/asd", "WTF/asd");
        SnipTreeTransformation stt = new SnipTreeTransformation(c);
        Page p = new Page();
        p.addMeta("title", "WTF");
        Assert.assertEquals("\n-   [WTF/qwe](WTF/qwe.html)\n-   [WTF/asd](WTF/asd.html)\n\n", stt.transform("{snip-tree:WTF}", p));
    }
}
