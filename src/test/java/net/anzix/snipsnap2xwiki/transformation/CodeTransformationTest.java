package net.anzix.snipsnap2xwiki.transformation;

import junit.framework.Assert;
import net.anzix.snipsnap2xwiki.Page;
import org.junit.Test;

public class CodeTransformationTest {

    @Test
    public void test() {
        CodeTransformation one = new CodeTransformation();
        CodeTransformation two = new CodeTransformation(one);
        String start = "{% highlight java %}";
        String end = "{% endhighlight %}";
        Assert.assertEquals(start + "\nstatic \\{System.out.println(\"asd\")\\}\n" + end, two.transform(one.transform("{code}\nstatic {System.out.println(\"asd\")}\n{code}", new Page()), new Page()));

        String txt = "{code}\n" +

                " /**\n" +
                "  * A metodus nev tetszoleges lehet....\n" +
                "  * @bigyo-depend logger\n" +
                "  */\n" +
                "  public void setSajatLogger(Logger log) {\n" +
                "{code}\n";

        String expected = start + "\n" +
                " /**\n" +
                "  * A metodus nev tetszoleges lehet....\n" +
                "  * @bigyo-depend logger\n" +
                "  */\n" +
                "  public void setSajatLogger(Logger log) \\{\n" +
                end + "\n";
        Assert.assertEquals(expected, two.transform(one.transform(txt, new Page()), new Page()));

        Assert.assertEquals(start + "\nasd\n" + end + "\nqwe\n" + start + "\nasd\n" + end + "\n", two.transform(one.transform("{code}\nasd\n{code}\nqwe\n{code}\nasd\n{code}\n", new Page()),new Page()));
    }
}
