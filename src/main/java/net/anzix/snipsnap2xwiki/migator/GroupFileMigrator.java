/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import java.io.File;
import java.util.List;
import net.anzix.snipsnap2xwiki.MigrationContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
 */
public class GroupFileMigrator extends AbstractMigrator {

    int userNo = 3;

    public GroupFileMigrator(MigrationContext context) {
        super(context);
    }

    @Override
    public void migrate(Element root) throws Exception {
        //initialize group file
        Element groupTemplate = null;
        SAXBuilder builder = new SAXBuilder();
        File groupFile = new File("src/main/template/XWikiAllGroup.xml");
        Document xwikiAllGroups = builder.build(groupFile).getDocument();
        List<Element> groupObjects = xwikiAllGroups.getRootElement().getChildren("object");
        for (Element e : groupObjects) {
            groupTemplate = e;
        }

        for (String name : getContext().getUsersMigrated()) {
            //add userToThe group file
            Element e = (Element) groupTemplate.clone();
            e.getChild("property").getChild("member").setText("XWiki." + name);
            e.getChild("number").setText("" + userNo++);
            xwikiAllGroups.getRootElement().addContent(e);

        }
        writeFile("XWiki", "XWikiAllGroup", xwikiAllGroups);
        getContext().pageMigratedSuccesfully("XWiki.XWikiAllGroup");
    }
}
