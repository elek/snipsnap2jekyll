/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.snipsnap2xwiki.transformation;

import net.anzix.snipsnap2xwiki.Page;

/**
 * Replace value with an other.
 */
public class ReplaceTransformation implements Transformation {

    private String replacementValue;

    public ReplaceTransformation(String replacementValue) {
        this.replacementValue = replacementValue;
    }

    @Override
    public String transform(String source, Page page) {
        return replacementValue;
    }
}
