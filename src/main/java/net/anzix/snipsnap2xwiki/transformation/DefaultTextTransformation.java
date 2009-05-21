/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.MigrationContext;

/**
 *
 * @author elek
 */
public class DefaultTextTransformation extends CompositeTransformation {

    public DefaultTextTransformation(MigrationContext context) {
        transformations.add(new SnipSnap2XWiki1(context));
        transformations.add(new ImageTransformation());
        transformations.add(new MacroListTransformation());

    }
}
