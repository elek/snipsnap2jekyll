/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author elek
 */
public class TransformOrigDump {

    private File oldFile;
    private File newDir;

    public TransformOrigDump(File oldFile, File newDir) {
        this.oldFile = oldFile;
        this.newDir = newDir;
    }

    public void migrate() throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();

            Document d = builder.build(oldFile);
            Element snipSnapDumpRoot = d.getRootElement();
            Element jumChild = null;
            for (Element child : (List<Element>) snipSnapDumpRoot.getChildren()) {
                if (child.getName().equals("snip")) {
                    if (child.getChildText("name").equals("JUM")) {
                        jumChild = child;
//                        d.getRootElement().removeContent(child);
                    }
                }
            }
            d.getRootElement().removeContent(jumChild);
            XMLOutputter outputter = new XMLOutputter();
            outputter.output(d, new FileWriter(new File("/tmp/filtered.snap")));

        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new TransformOrigDump(new File(args.length < 1 ? "/home/elek/jhacks.snip" : args[0]), new File(args.length < 2 ? "/tmp/xwiki" : args[1])).migrate();

    }
}
