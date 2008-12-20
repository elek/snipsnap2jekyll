/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.migator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.anzix.snipsnap2xwiki.*;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
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

    public void copyComments(String name, Element newRoot) throws JDOMException, IOException {
        //comments?
        if (getContext().getCommentsCache().get(name) != null) {
            for (Element commentSnipElement : getContext().getCommentsCache().get(name)) {
                Document commentDoc = builder.build(new File("src/main/template/comment.xml"));
                Element commentRoot = (Element) commentDoc.getRootElement().clone();

                //author of comment
                Element p = new Element("property");
                Element t = new Element("author");
                p.addContent(t);
                t.addContent("XWiki." + commentSnipElement.getChildText("cUser"));
                commentRoot.addContent(p);

                //text of comment
                p = new Element("property");
                t = new Element("comment");
                p.addContent(t);
                t.addContent(getContext().getSyntaxTransformation().transform(commentSnipElement.getChildText("content")));
                commentRoot.addContent(p);

                //date of comment
                p = new Element("property");
                t = new Element("date");
                p.addContent(t);
                Date createdTime = new Date(Long.parseLong(commentSnipElement.getChildText("cTime")));
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd H:m:s.S");
                t.addContent(dateFormatter.format(createdTime));
                commentRoot.addContent(p);

                newRoot.addContent(commentRoot);
            }

        }
    }

    public void fixObjextNames(String name, Element newRoot) {
        //replace object name tags
        List<Element> objects = newRoot.getChildren("object");
        for (Element object : objects) {
            object.getChild("name").setText("XWiki." + name);
        }
    }
}
