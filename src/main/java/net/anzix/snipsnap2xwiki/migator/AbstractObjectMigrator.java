/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import net.anzix.snipsnap2xwiki.*;
import java.util.List;
import org.jdom.Element;

/**
 *
 * @author elek
 */
public abstract class AbstractObjectMigrator extends AbstractMigrator {

    public AbstractObjectMigrator(MigrationContext context) {
        super(context);
    }

    public void migrate(Element root) throws Exception {
        List<Element> childs = root.getChildren();
        for (Element e : childs) {
            if (includeInMigration(e)) {
                migrateObject(e);
            }
        }
    }

    protected abstract boolean includeInMigration(Element e);

    public abstract void migrateObject(Element e) throws Exception;

    protected void addTag(Element parent, String name, String value) {
        Element element = new Element(name);
        element.setText(value);
        parent.addContent(element);
    }
}
