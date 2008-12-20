/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.transformation.Transformation;

/**
 *
 * @author elek
 */
public class AddPrefix implements Transformation {

    private String prefix;

    public AddPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String transform(String source) {
        return prefix + source;
    }

}
