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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Split dump file to separated files.
 *
 * @author elek
 */
public class SplitDump {

    @Argument(index = 0, metaVar = "dump_file", usage = "SnipSnap wiki dump file", required = true)
    private File oldFile;

    @Argument(index = 1, metaVar = "dest_dir", usage = "Destination directory")
    private File newDir;


    public void migrate() throws Exception {
        try {
            if (!newDir.exists()) {
                newDir.mkdirs();
            }
            SAXBuilder builder = new SAXBuilder();

            Document d = builder.build(oldFile);
            Element snipSnapDumpRoot = d.getRootElement();
            Element jumChild = null;
            int i = 0;
            XMLOutputter outputter = new XMLOutputter();
            for (Element child : (List<Element>) snipSnapDumpRoot.getChildren()) {
                String name = child.getName();
                String fileName = "" + i++;
                if (name.equals("snip")) {
                    fileName = child.getChildText("name");
                }
                if (name.equals("user")) {
                    fileName = child.getChildText("login");
                }
                File output = new File(new File(newDir, name), fileName + ".html");
                if (!output.getParentFile().exists()) {
                    output.getParentFile().mkdirs();
                }
                outputter.output(child, new FileWriter(output));

            }
            d.getRootElement().removeContent(jumChild);


        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SplitDump dump = new SplitDump();
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
