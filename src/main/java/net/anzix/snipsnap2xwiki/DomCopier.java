/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.anzix.snipsnap2xwiki.transformation.NullTransformation;
import net.anzix.snipsnap2xwiki.transformation.Transformation;
import org.jdom.Element;

/**
 *
 * @author elek
 */
public class DomCopier {

    Element oldRoot;
    Element newRoot;

    public DomCopier(Element oldRoot, Element newRoot) {
        this.oldRoot = oldRoot;
        this.newRoot = newRoot;
    }

    public void copyText(String from, String to) {
        copyText(from, to, new NullTransformation());
    }

    public void copyText(String from, String to, Transformation transform) {
        String string = oldRoot.getChildText(from);
        Element ne = newRoot.getChild(to);
        if (ne == null) {
            ne = new Element(to);
            newRoot.addContent(ne);
        }
        ne.setText(transform.transform(string));
    }

    public void copyTextToObjectProperty(String sourceProperty, String destObjectPath, String destPropertyPath) {
        copyTextToObjectProperty(sourceProperty, destObjectPath, destPropertyPath, new NullTransformation());
    }

    public void copyTextToObjectProperty(String sourceProperty, String destObjectPath, String destPropertyPath, Transformation transformation) {
        Element object = findObject(newRoot, destObjectPath);
        List<Element> properties = object.getChildren("property");
        for (Element propertyE : properties) {
            List<Element> children = propertyE.getChildren();
            for (Element child : children) {
                if (child.getName().equals(destPropertyPath)) {
                    child.setText(transformation.transform(oldRoot.getChildText(sourceProperty)));
                    return;
                }
            }

        }
    }

    protected Element findObject(Element root, String objectPath) {
        String[] pathElements = parseObjectPath(objectPath);
        return findObject(root, pathElements[0], Integer.parseInt(pathElements[1]));
    }

    protected Element findObject(Element root, String className, int index) {
        int currIdx = 0;
        List<Element> objects = root.getChildren("object");
        for (Element object : objects) {
            if (object.getChild("className").getText().equals(className) && currIdx == index) {
                return object;
            }
            currIdx++;
        }
        throw new IllegalArgumentException("No such object: " + className);
    }

    public Element getNewRoot() {
        return newRoot;
    }

    protected static String[] parseObjectPath(String objectPath) {
        Pattern p = Pattern.compile("([A-Za-z\\.]+)(\\[([0-9]+)\\])?");
        Matcher m = p.matcher(objectPath);
        if (!m.matches()) {
            throw new IllegalArgumentException("Wrong objectPath format: " + objectPath);
        }
        String addr = m.group(1);
        String idx = m.group(3);
        if (idx == null) {
            idx = "0";
        }

        return new String[]{addr, idx};
    }
}
