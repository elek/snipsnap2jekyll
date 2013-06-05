package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Add missing line ends and migrate snipsnap list to markdown.
 */
public class LineEndAndListTransformation implements Transformation {

    Pattern list;
    Pattern numberedList;
    Pattern header;

    public LineEndAndListTransformation() {
        this.list = Pattern.compile("^\\s*([-\\*]+|1\\.)\\s+(.*)$");
        this.header = Pattern.compile("^(#+)\\s+(.*)$");
    }

    @Override
    public String transform(String source, Page page) {
        String prevLine = "\n";
        boolean prevIsHeader = false;
        boolean prevIsList = false;
        StringBuilder b = new StringBuilder();
        for (String line : source.split("\n")) {
            if (prevIsHeader) {
                if (!isEmpty(line)) {
                    b.append("\n");
                }
                prevIsHeader = false;
            }
            Matcher listMatcher = list.matcher(line);
            Matcher headerMatcher = header.matcher(line);
            if (headerMatcher.matches()) {
                if (!isEmpty(prevLine)) {
                    b.append("\n");
                }
                b.append(line + "\n");
                prevIsHeader = true;
            } else if (listMatcher.matches()) {
                if (prevIsList == false && !isEmpty(prevLine)) {
                    b.append("\n");
                }
                prevIsList = true;
                if (listMatcher.group(1).contains("1")) {
                    b.append("1.   ");
                } else {
                    for (int i = 0; i < listMatcher.group(1).trim().length() - 1; i++) {
                        b.append("    ");
                    }
                    b.append("*   ");

                }

                b.append(listMatcher.group(2) + "\n");

            } else {
                if (prevIsList && !isEmpty(line)) {
                    b.append("\n");
                }
                b.append(line + "\n");
                prevIsList = false;

            }

            prevLine = line;
        }

        return b.toString();
    }

    public boolean isEmpty(String line) {
        return line.trim().length() == 0;
    }
}
