/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migrator;

import net.anzix.snipsnap2xwiki.MigrationContext;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Base class for migrate dom elements one-by-one.
 */
public abstract class AbstractObjectMigrator extends AbstractMigrator {

    SAXBuilder builder = new SAXBuilder();

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


    public void fixObjextNames(String name, Element newRoot) {
        //replace object name tags
        List<Element> objects = newRoot.getChildren("object");
        for (Element object : objects) {
            object.getChild("name").setText("XWiki." + name);
        }
    }
}
