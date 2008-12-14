/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import java.io.File;
import net.anzix.snipsnap2xwiki.MigrationContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
 */
public class NewsMigrator extends AbstractObjectMigrator {

    public NewsMigrator(MigrationContext context) {
        super(context);
    }

    @Override
    protected boolean includeInMigration(Element e) {
        if (e.getName().equals("user")) {
            return false;
        }
        String name = e.getChildText("name");
        return (name.startsWith("topics/") || name.startsWith("start/"));


    }

    @Override
    public void migrateObject(Element oldRoot) throws Exception {
        System.out.println(oldRoot.getChildText("name"));
        String name = oldRoot.getChildText("name");

        SAXBuilder builder = new SAXBuilder();
        Document d = builder.build(new File("src/main/template/Page.xml"));
    }
}
