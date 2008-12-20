/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import java.io.File;
import java.io.FileWriter;
import net.anzix.snipsnap2xwiki.MigrationContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author elek
 */
public class PackageFileMigrator extends AbstractMigrator {

    public PackageFileMigrator(MigrationContext context) {
        super(context);
    }

    @Override
    public void migrate(Element root) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        Document descriptor = builder.build("src/main/template/package.xml");
        for (String userName : getContext().getUsersMigrated()) {
            addPageToDescriptorFile(descriptor, "XWiki." + userName);
        }

        for (String userName : getContext().getPagesMigrated()) {
            addPageToDescriptorFile(descriptor, userName);

        }
        XMLOutputter outputter = new XMLOutputter();
        outputter.output(descriptor, new FileWriter(new File(getContext().getOutputDir(), "package.xml")));
    }

    private void addPageToDescriptorFile(Document descriptor, String pageName) {
        Element files = descriptor.getRootElement().getChild("files");
        Element file = new Element("file");
        file.setAttribute("defaultAction", "0");
        file.setAttribute("language", "");
        file.setText(pageName);
        files.addContent(file);
        files.addContent("\n");
    }
}
