/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author elek
 */
public class TransformOrigDump {

    @Option(name = "--exclude", usage = "Page name to be excluded from the dump", required = true)
    private String exclude;

    @Argument(index = 0, metaVar = "dump_file", usage = "SnipSnap wiki dump file", required = true)
    private File oldFile;

    @Argument(index = 1, metaVar = "dest_dir", usage = "Destination directory")
    private File newDir;


    public TransformOrigDump() {
    }

    public void migrate() throws Exception {
        try {
            if (newDir == null) {
                newDir = oldFile.getParentFile();
            }

            SAXBuilder builder = new SAXBuilder();

            Document d = builder.build(oldFile);
            Element snipSnapDumpRoot = d.getRootElement();
            Element jumChild = null;
            for (Element child : (List<Element>) snipSnapDumpRoot.getChildren()) {
                if (child.getName().equals("snip") && child.getChildText("name").equals(exclude)) {
                    jumChild = child;

                }
            }
            d.getRootElement().removeContent(jumChild);
            XMLOutputter outputter = new XMLOutputter();
            outputter.output(d, new FileWriter(new File(newDir, oldFile.getName() + ".filtered")));

        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        TransformOrigDump dump = new TransformOrigDump();
        CmdLineParser parser = new CmdLineParser(dump);
        try {
            parser.parseArgument(args);
            dump.migrate();
        } catch (CmdLineException ex) {
            System.out.println("ERROR: " + ex.getMessage() + "\n");
            parser.printSingleLineUsage(System.out);
            System.out.println("\n\n");
            parser.printUsage(System.out);
        }


    }
}
