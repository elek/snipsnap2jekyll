package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transform a long date to formatted date.
 */
public class DateTransformer implements Transformation {
    private SimpleDateFormat sdf;

    public DateTransformer() {
        this("yyyy-MM-dd HH:mm:ss Z");
    }

    public DateTransformer(String format) {
        this.sdf = new SimpleDateFormat(format);
    }

    @Override
    public String transform(String source, Page page) {
        if (source != null && source.trim().length() > 0) {
            return sdf.format(new Date(Long.parseLong(source)));
        } else {
            return source;
        }
    }
}
