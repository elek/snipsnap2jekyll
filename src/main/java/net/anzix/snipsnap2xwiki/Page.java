package net.anzix.snipsnap2xwiki;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Jekyll page builder object.
 */
public class Page {

    private String content;

    private Map<String, Object> metadata = new HashMap<String, Object>();

    public void addMeta(String layout, String aDefault) {
        metadata.put(layout, aDefault);
    }

    public Object getMetadata(String key) {
        return metadata.get(key);

    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public String getContent() {
        return content;
    }

    public void write(File output) {
        try {
            FileWriter writer = new FileWriter(output);
            writer.write("---\n");
            for (String key : metadata.keySet()) {
                writer.write(String.format("%s: %s \n", key, metadata.get(key)));
            }
            writer.write("---\n");
            writer.write(content);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setContent(String transform) {
        this.content = transform;
    }

    public Object getMeta(String title) {
        return metadata.get(title);
    }
}
