/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki;

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
        copyText(from, to, new Transformation() {

            @Override
            public String transform(String source) {
                return source;
            }
        });
    }

    public void copyText(String from, String to, Transformation transform) {
        String string = oldRoot.getChildText(from);
        Element ne = newRoot.getChild(to);
        if(ne == null) {
        	ne = new Element(to);
        	newRoot.addContent(ne);
        }
        ne.setText(transform.transform(string));
    }

    public Element getNewRoot() {
        return newRoot;
    }
}
