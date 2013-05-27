/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.MigrationContext;

/**
 * Default set of the transformations.
 * @author elek
 */
public class DefaultTextTransformation extends CompositeTransformation {

    public DefaultTextTransformation(MigrationContext context) {
        transformations.add(new TitleExtractor());
        transformations.add(new HeaderTransformation());
        transformations.add(new SecondHeaderTransformation());
        CodeTransformation firstPhase = new CodeTransformation();
        transformations.add(firstPhase);
        transformations.add(new LineEndAndListTransformation());
        transformations.add(new SnipSnap2Markdown(context));
        transformations.add(new LinkTransformation());
        transformations.add(new ItalicTransformer());
        transformations.add(new ImageTransformation());
        transformations.add(new RemoveMacrosTransformation());
        transformations.add(new NoMacroTransformation());
        transformations.add(new CodeTransformation(firstPhase));


    }
}
