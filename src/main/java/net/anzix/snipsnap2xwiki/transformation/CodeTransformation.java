package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Traform code blocks
 */
public class CodeTransformation extends RegExpTransformation {

    private boolean inside;

    private CodeTransformation original;

    private Queue<String> codeBlocks = new LinkedList();

    public static final String UNIQUE_TOKEN = "HERE_WILL_BE_A_CODE_BLOCK";

    /**
     * First phase
     */
    public CodeTransformation() {
        super(".*");
        pattern = Pattern.compile("\\{code(?:[^\\}]+)?\\}(.+?)\\{code\\}", Pattern.DOTALL);
    }

    /*
    * second phase
     */
    public CodeTransformation(CodeTransformation original) {
        super(UNIQUE_TOKEN);
        this.original = original;
    }


    @Override
    public String replace(Matcher m, Page page) {
        if (original != null) {
            return "{% highlight java %}" + original.codeBlocks.poll() + "{% endhighlight %}";
        } else {
            codeBlocks.add(m.group(1).
                    replaceAll("\\}", "\\\\}").
                    replaceAll("\\{", "\\\\{"));
            return UNIQUE_TOKEN;

        }
    }
}
